package com.yjtech.wisdom.tourism.marketing.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.zc.params.ZcOtaEvaluateParam;
import com.yjtech.wisdom.tourism.common.bean.zc.po.ZcOtaEvaluatePO;
import com.yjtech.wisdom.tourism.common.service.ZcInfoSyncService;
import com.yjtech.wisdom.tourism.marketing.entity.MarketingEvaluateEntity;
import com.yjtech.wisdom.tourism.marketing.mapper.MarketingEvaluateMapper;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateListDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateScreenQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void syncCreate(ZcOtaEvaluateParam params){
        List<MarketingEvaluateEntity> saveList = new ArrayList<>();
        //构建页码
        long pageNum = 1L;
        //获取评价信息
        List<ZcOtaEvaluatePO> evaluateList = zcInfoSyncService.getEvaluateList(params);
        //分页获取
        while (!evaluateList.isEmpty()) {
            for(ZcOtaEvaluatePO zcOtaEvaluatePO : evaluateList){
                MarketingEvaluateEntity entity = new MarketingEvaluateEntity();
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
     * 查询评价统计
     * @param vo
     * @return
     */
    public MarketingEvaluateStatisticsDTO queryEvaluateStatistics(EvaluateScreenQueryVO vo){
        return baseMapper.queryEvaluateStatistics(vo);
    }

    /**
     * 查询评价类型分布
     * @param vo
     * @return
     */
    public List<BasePercentVO> queryEvaluateTypeDistribution(EvaluateScreenQueryVO vo){
        return baseMapper.queryEvaluateTypeDistribution(vo);
    }

    /**
     * 查询评价热词排行
     * @param vo
     * @return
     */
    public List<BaseVO> queryEvaluateHotRank(EvaluateScreenQueryVO vo){
        return baseMapper.queryEvaluateHotRank(vo);
    }

    /**
     * 查询评价分页列表
     * @param vo
     * @return
     */
    public IPage<MarketingEvaluateListDTO> queryForPage(EvaluateScreenQueryVO vo){
        return baseMapper.queryForPage(new Page(vo.getPageNo(), vo.getPageSize()), vo);
    }

}
