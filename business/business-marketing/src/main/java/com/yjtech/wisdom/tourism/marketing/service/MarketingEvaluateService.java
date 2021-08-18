package com.yjtech.wisdom.tourism.marketing.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.AnalysisMonthChartInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.zc.params.ZcOtaEvaluateParam;
import com.yjtech.wisdom.tourism.common.bean.zc.po.ZcOtaEvaluatePO;
import com.yjtech.wisdom.tourism.common.core.domain.StatusParam;
import com.yjtech.wisdom.tourism.common.service.ZcInfoSyncService;
import com.yjtech.wisdom.tourism.common.utils.DateUtils;
import com.yjtech.wisdom.tourism.marketing.entity.MarketingEvaluateEntity;
import com.yjtech.wisdom.tourism.marketing.mapper.MarketingEvaluateMapper;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.HotelEvaluateSatisfactionRankDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateListDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateQueryVO;
import com.yjtech.wisdom.tourism.mybatis.utils.AnalysisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 营销推广 评价信息
 *
 * @Author horadirm
 * @Date 2020/11/20 16:12
 */
@Service
public class MarketingEvaluateService extends ServiceImpl<MarketingEvaluateMapper, MarketingEvaluateEntity> {

    @Autowired
    private ZcInfoSyncService zcInfoSyncService;
    @Resource
    private MarketingEvaluateMapper marketingEvaluateMapper;


    /**
     * 同步评价信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void syncCreate(ZcOtaEvaluateParam params) {
        List<MarketingEvaluateEntity> saveList = new ArrayList<>();
        //构建页码
        long pageNum = 1L;
        //获取评价信息
        List<ZcOtaEvaluatePO> evaluateList = zcInfoSyncService.getEvaluateList(params);
        //分页获取
        while (!evaluateList.isEmpty()) {
            for (ZcOtaEvaluatePO zcOtaEvaluatePO : evaluateList) {
                MarketingEvaluateEntity entity = new MarketingEvaluateEntity();
                entity.build(zcOtaEvaluatePO, params);

                saveList.add(entity);
            }
            //获取下页
            params.setPageNum(++pageNum);
            evaluateList = zcInfoSyncService.getEvaluateList(params);
        }

        if (!saveList.isEmpty()) {
            marketingEvaluateMapper.insertBatch(saveList);
        }
    }

    /**
     * 启/停用
     *
     * @param statusParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(StatusParam statusParam) {
        //获取评价信息
        MarketingEvaluateEntity evaluateEntity = baseMapper.selectById(statusParam.getId());
        Assert.notNull(evaluateEntity, "更新状态失败：评价信息不存在");

        evaluateEntity.setEquipStatus(statusParam.getEquipStatus());

        return baseMapper.updateById(evaluateEntity);
    }

    /**
     * 查询评价统计
     *
     * @param vo
     * @return
     */
    public MarketingEvaluateStatisticsDTO queryEvaluateStatistics(EvaluateQueryVO vo) {
        return baseMapper.queryEvaluateStatistics(vo);
    }

    /**
     * 查询评价类型分布
     *
     * @param vo
     * @return
     */
    public List<BasePercentVO> queryEvaluateTypeDistribution(EvaluateQueryVO vo) {
        return baseMapper.queryEvaluateTypeDistribution(vo);
    }

    /**
     * 查询评价对象分布
     *
     * @param vo
     * @return
     */
    public List<BasePercentVO> queryEvaluateObjectDistribution(EvaluateQueryVO vo) {
        return baseMapper.queryEvaluateObjectDistribution(vo);
    }

    /**
     * 查询评价热词排行
     *
     * @param vo
     * @return
     */
    public List<BaseVO> queryEvaluateHotRank(EvaluateQueryVO vo) {
        return baseMapper.queryEvaluateHotRank(vo);
    }

    /**
     * 查询评价排行
     *
     * @param vo
     * @return
     */
    public IPage<BaseVO> queryEvaluateRank(EvaluateQueryVO vo) {
        return baseMapper.queryEvaluateRank(new Page(vo.getPageNo(), vo.getPageSize()), vo);
    }

    /**
     * 查询满意度排行
     *
     * @param vo
     * @return
     */
    public IPage<HotelEvaluateSatisfactionRankDTO> queryEvaluateSatisfactionRank(EvaluateQueryVO vo) {
        return baseMapper.queryEvaluateSatisfactionRank(new Page(vo.getPageNo(), vo.getPageSize()), vo);
    }

    /**
     * 查询评价分页列表
     *
     * @param vo
     * @return
     */
    public IPage<MarketingEvaluateListDTO> queryForPage(EvaluateQueryVO vo) {
        return baseMapper.queryForPage(new Page(vo.getPageNo(), vo.getPageSize()), vo);
    }

    /**
     * 查询评价量趋势、同比、环比
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<AnalysisBaseInfo> queryEvaluateAnalysis(EvaluateQueryVO vo) {
        //初始化当年月份信息
        List<String> monthMarkList = DateUtils.getEveryMonthOfCurrentYear();

        //获取当前年度月趋势信息
        List<AnalysisMonthChartInfo> currentAnalysisMonthInfo = this.baseMapper.queryEvaluateCurrentAnalysis(vo);
        //获取去年度月趋势信息
        List<AnalysisMonthChartInfo> lastAnalysisMonthInfo = this.baseMapper.queryEvaluateLastAnalysis(vo);

        return AnalysisUtils.buildAnalysisInfo(monthMarkList, currentAnalysisMonthInfo, lastAnalysisMonthInfo);
    }

    /**
     * 查询评价满意度趋势、同比、环比
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<AnalysisBaseInfo> queryEvaluateSatisfactionAnalysis(EvaluateQueryVO vo) {
        //初始化当年月份信息
        List<String> monthMarkList = DateUtils.getEveryMonthOfCurrentYear();

        //获取当前年度月趋势信息
        List<AnalysisMonthChartInfo> currentAnalysisMonthInfo = this.baseMapper.queryEvaluateSatisfactionCurrentAnalysis(vo);
        //获取去年度月趋势信息
        List<AnalysisMonthChartInfo> lastAnalysisMonthInfo = this.baseMapper.queryEvaluateSatisfactionLastAnalysis(vo);

        return AnalysisUtils.buildAnalysisInfo(monthMarkList, currentAnalysisMonthInfo, lastAnalysisMonthInfo);
    }

    /**
     * 景区大数据——评价排行
     *
     * @return
     */
    public IPage<BaseVO> queryEvaluateTop5(EvaluateQueryVO query) {
        return baseMapper.queryEvaluateTop5(new Page(query.getPageNo(), query.getPageSize()), query);
    }

    /**
     * 景区大数据——满意度排行
     *
     * @return
     */
    public IPage<BaseVO> querySatisfactionTop5(EvaluateQueryVO query) {
        return baseMapper.querySatisfactionTop5(new Page(query.getPageNo(), query.getPageSize()), query);
    }

    /**
     * 景区分布——查询评价统计
     *
     * @param vo
     * @return
     */
    public List<BasePercentVO> queryScenicEvaluateTypeDistribution(EvaluateQueryVO vo) {
        return baseMapper.queryScenicEvaluateTypeDistribution(vo);
    }

    /**
     * 景区分布——查询评价统计
     *
     * @param vo
     * @return
     */
    public MarketingEvaluateStatisticsDTO queryScenicEvaluateStatistics(EvaluateQueryVO vo) {
        return baseMapper.queryScenicEvaluateStatistics(vo);
    }

    /**
     * 景区分布——热度趋势
     *
     * @param query
     * @return
     */
    public List<BaseVO> queryHeatTrend(EvaluateQueryVO query) {
        return baseMapper.queryHeatTrend(query);
    }

    /**
     * 景区分布——满意度趋势
     *
     * @param query
     * @return
     */
    public List<BaseVO> querySatisfactionTrend(EvaluateQueryVO query) {
        return baseMapper.querySatisfactionTrend(query);
    }

    /**
     * 景区分布——热词
     *
     * @param vo
     * @return
     */
    public List<BaseVO> queryScenicHotRank(EvaluateQueryVO vo) {
        return baseMapper.queryScenicHotRank(vo);
    }
}
