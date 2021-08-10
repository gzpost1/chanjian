package com.yjtech.wisdom.tourism.marketing.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.zc.params.ZcOtaEvaluateParam;
import com.yjtech.wisdom.tourism.common.bean.zc.po.ZcOtaEvaluatePO;
import com.yjtech.wisdom.tourism.common.enums.DataSourceTypeEnum;
import com.yjtech.wisdom.tourism.common.service.ZcInfoSyncService;
import com.yjtech.wisdom.tourism.common.utils.DateUtils;
import com.yjtech.wisdom.tourism.marketing.entity.TbMarketingEvaluateEntity;
import com.yjtech.wisdom.tourism.marketing.mapper.MarketingEvaluateMapper;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.AnalysisMonthChartInfo;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.TouristAttentionScreenDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.evaluate.*;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateRankingVO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateScreenQueryVO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.ScreenAnalysisQueryVO;
import com.yjtech.wisdom.tourism.marketing.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 营销推广 评价信息
 *
 * @Author horadirm
 * @Date 2020/11/20 16:12
 */
@Service
public class MarketingEvaluateService extends ServiceImpl<MarketingEvaluateMapper, TbMarketingEvaluateEntity> {

    @Autowired
    private ZcInfoSyncService zcInfoSyncService;
    @Resource
    private MarketingEvaluateMapper marketingEvaluateMapper;


    /**
     * 同步评价信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void syncCreate(ZcOtaEvaluateParam params){
        List<TbMarketingEvaluateEntity> saveList = new ArrayList<>();
        //构建页码
        long pageNum = 1L;
        //获取评价信息
        List<ZcOtaEvaluatePO> evaluateList = zcInfoSyncService.getEvaluateList(params);
        //分页获取
        while (!evaluateList.isEmpty()) {
            for(ZcOtaEvaluatePO zcOtaEvaluatePO : evaluateList){
                TbMarketingEvaluateEntity entity = new TbMarketingEvaluateEntity();
                entity.build(zcOtaEvaluatePO, params);

                saveList.add(entity);
            }
            //获取下页
            params.setPageNum(++pageNum);
            evaluateList = zcInfoSyncService.getEvaluateList(params);
        }

        if(!saveList.isEmpty()){
            marketingEvaluateMapper.insertBatch(saveList);
        }
    }

    /**
     * 获取 游客关注度 大屏信息
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public TouristAttentionScreenDTO getAttentionScreen(ScreenAnalysisQueryVO vo){
        //获取评论排名
        EvaluateRankingVO rankingVO = new EvaluateRankingVO();
        rankingVO.buildScreenParams(vo, Arrays.asList(new Integer[]{DataSourceTypeEnum.DATA_SOURCE_TYPE_SCENIC_SPOT.getValue()}));

        List<EvaluateRankingInfo> rankingInfo = marketingEvaluateMapper.getRankingInfo(rankingVO);
        //获取热词列表
        List<EvaluateHotInfo> impression = null;
        //获取行业分布
        List<EvaluateDistributionInfo> businessInfo = marketingEvaluateMapper.getBusinessInfo(vo);
        //获取来源分布
        List<EvaluateDistributionInfo> sourcesInfo = marketingEvaluateMapper.getSourcesInfo(vo);

        //获取评论日趋势信息
        List<EvaluateAnalysisDayChartInfo> analysisDayInfo = marketingEvaluateMapper.getAnalysisDayInfo(vo.getAreaCode());
        //初始化当年天信息
        List<String> dayMarkList = DateUtils.getEveryDayOfCurrentYear();
        for(String dayMark : dayMarkList){
            //匹配标识
            boolean match = false;
            for(EvaluateAnalysisDayChartInfo info : analysisDayInfo){
                //如果日期匹配，则继续查找
                if(dayMark.equals(info.getEvaluateTime())){
                    match = true;
                    break;
                }
            }
            //如果日期未匹配，则设置默认值
            if(!match){
                EvaluateAnalysisDayChartInfo intiInfo = new EvaluateAnalysisDayChartInfo(dayMark);
                analysisDayInfo.add(intiInfo);
            }
        }
        //排序
        Collections.sort(analysisDayInfo, new Comparator<EvaluateAnalysisDayChartInfo>() {
            @Override
            public int compare(EvaluateAnalysisDayChartInfo o1, EvaluateAnalysisDayChartInfo o2) {
                if(o1.getEvaluateTime().compareTo(o2.getEvaluateTime()) == 0){
                    return -1;
                }
                return o1.getEvaluateTime().compareTo(o2.getEvaluateTime());
            }
        });
        //获取日趋势统计信息
        EvaluateAnalysisDayDTO day = marketingEvaluateMapper.getAnalysisDayStatistics(vo.getAreaCode());
        AnalysisBaseInfo analysisBaseInfo = new AnalysisBaseInfo(dayMarkList.get(0).substring(0, 4), analysisDayInfo);
        day.setAnalysisDayChart(analysisBaseInfo);

        //初始化当年月份信息
        List<String> monthMarkList = DateUtils.getEveryMonthOfCurrentYear();

        //获取去年度月趋势信息
        List<AnalysisMonthChartInfo> lastAnalysisMonthInfo = marketingEvaluateMapper.getLastAnalysisMonthInfo(vo.getAreaCode());
        //构建去年月趋势信息
        List<AnalysisMonthChartInfo> lastData = new ArrayList<>();
        //获取当前年度月趋势信息
        List<AnalysisMonthChartInfo> currentAnalysisMonthInfo = marketingEvaluateMapper.getCurrentAnalysisMonthInfo(vo.getAreaCode());
        //构建今年月趋势信息
        List<AnalysisMonthChartInfo> currentData = new ArrayList<>();

        ServiceUtils.buildAnalysisInfo(monthMarkList, currentAnalysisMonthInfo, lastAnalysisMonthInfo, currentData, lastData);

        //获取月趋势统计
        EvaluateAnalysisMonthDTO month = marketingEvaluateMapper.getAnalysisMonthStatistics(vo.getAreaCode());
        month.setAnalysisMonthChart(Arrays.asList(
                //封装今年月趋势信息
                new AnalysisBaseInfo(currentData.get(0).getXTime().substring(0, 4), currentData),
                //封装去年月趋势信息
                new AnalysisBaseInfo(lastData.get(0).getXTime().substring(0, 4), lastData)));


        return new TouristAttentionScreenDTO(rankingInfo, impression, businessInfo, sourcesInfo, month, day);
    }

    /**
     * 查询评价类型分布
     * @param vo
     * @return
     */
    public List<BasePercentVO> queryEvaluateTypeDistribution(EvaluateScreenQueryVO vo){
        return baseMapper.queryEvaluateTypeDistribution(vo);
    }

}
