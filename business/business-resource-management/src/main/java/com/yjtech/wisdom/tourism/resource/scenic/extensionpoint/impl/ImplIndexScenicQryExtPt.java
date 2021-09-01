package com.yjtech.wisdom.tourism.resource.scenic.extensionpoint.impl;

import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.bean.index.ScenicBuildingDTO;
import com.yjtech.wisdom.tourism.common.bean.index.TodayRealTimeStatisticsDTO;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanUtils;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateQueryVO;
import com.yjtech.wisdom.tourism.marketing.service.MarketingEvaluateService;
import com.yjtech.wisdom.tourism.mybatis.entity.IndexQueryVO;
import com.yjtech.wisdom.tourism.resource.scenic.extensionpoint.IndexScenicQryExtPt;
import com.yjtech.wisdom.tourism.resource.scenic.extensionpoint.ScenicExtensionConstant;
import com.yjtech.wisdom.tourism.resource.scenic.service.ScenicService;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketSummaryQuery;
import com.yjtech.wisdom.tourism.resource.ticket.service.TicketHourSummaryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 综合总览景区模拟数据扩展点
 *
 * @author xulei
 * @create 2021-07-14 14:56
 */
@Extension(bizId = ExtensionConstant.SCENIC,
        useCase = ScenicExtensionConstant.INDEX_SCENIC_QUANTITY,
        scenario = ExtensionConstant.SCENARIO_IMPL)
public class ImplIndexScenicQryExtPt implements IndexScenicQryExtPt {

    @Autowired
    private TicketHourSummaryService ticketHourSummaryService;
    @Autowired
    private ScenicService scenicService;
    @Autowired
    private MarketingEvaluateService marketingEvaluateService;

    @Override
    public TodayRealTimeStatisticsDTO queryVisitStatistics(TicketSummaryQuery query) {
        TodayRealTimeStatisticsDTO todayRealTimeStatisticsDTO = new TodayRealTimeStatisticsDTO();
        Long todayScenicReception = ticketHourSummaryService.queryVisitStatistics(query);
        todayRealTimeStatisticsDTO.setTodayReception(todayScenicReception);
        //获取景区承载量
        Long scenicBearCapacity = scenicService.queryScenicBearCapacity();
        //计算承载度
        BigDecimal bearCapacityPercent = null == scenicBearCapacity || 0 == scenicBearCapacity ? BigDecimal.ZERO :
                new BigDecimal(todayScenicReception).divide(new BigDecimal(scenicBearCapacity), 3, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        todayRealTimeStatisticsDTO.setScenic(bearCapacityPercent);
        return todayRealTimeStatisticsDTO;
    }


    @Override
    public ScenicBuildingDTO scenicBuilding(IndexQueryVO vo) {
        //获取景区评论统计
        MarketingEvaluateStatisticsDTO evaluateStatistics = marketingEvaluateService.queryScenicEvaluateStatistics(buildEvaluateCondition(vo));

        //根据票务统计获取接待人数
        //构建票务查询条件
        TicketSummaryQuery ticketSummaryQuery = BeanUtils.copyBean(vo, TicketSummaryQuery.class);
        return new ScenicBuildingDTO(ticketHourSummaryService.queryVisitStatistics(ticketSummaryQuery), evaluateStatistics.getEvaluateTotal(), evaluateStatistics.getSatisfaction());
    }


    /**
     * 构建评论查询条件
     *
     * @param vo
     * @return
     */
    private EvaluateQueryVO buildEvaluateCondition(IndexQueryVO vo) {
        //构建评价查询条件
        EvaluateQueryVO evaluateQueryVO = BeanUtils.copyBean(vo, EvaluateQueryVO.class);
        //默认场所状态-启用
        evaluateQueryVO.setStatus(EntityConstants.ENABLED);
        //默认评价状态-启用
        evaluateQueryVO.setEquipStatus(EntityConstants.ENABLED);

        return evaluateQueryVO;
    }
}
