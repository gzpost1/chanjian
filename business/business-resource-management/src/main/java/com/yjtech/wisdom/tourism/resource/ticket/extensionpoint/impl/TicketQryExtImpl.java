package com.yjtech.wisdom.tourism.resource.ticket.extensionpoint.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.resource.ticket.extensionpoint.TicketExtensionConstant;
import com.yjtech.wisdom.tourism.resource.ticket.extensionpoint.TicketQryExtPt;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketRankingQuery;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketSaleQuantityQuery;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketSumaryQuery;
import com.yjtech.wisdom.tourism.resource.ticket.service.TicketChannelHourSummaryService;
import com.yjtech.wisdom.tourism.resource.ticket.service.TicketHourSummaryService;
import com.yjtech.wisdom.tourism.resource.ticket.service.TicketModelHourSummaryService;
import com.yjtech.wisdom.tourism.resource.ticket.service.TicketVisitorSourceHourSummaryService;
import com.yjtech.wisdom.tourism.resource.ticket.vo.SaleTrendVO;
import com.yjtech.wisdom.tourism.resource.ticket.vo.TicketRankingVO;
import com.yjtech.wisdom.tourism.resource.ticket.vo.VisitorSourceInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 票务真实数据扩展点
 *
 * @author liuhong
 * @date 2021-07-13 11:12
 */
@Extension(bizId = ExtensionConstant.BIZ_TICKET,
        useCase = TicketExtensionConstant.USE_CASE_STATISTIC,
        scenario = ExtensionConstant.SCENARIO_IMPL)
public class TicketQryExtImpl implements TicketQryExtPt {
    @Autowired
    private TicketHourSummaryService ticketHourSummaryService;

    @Autowired
    private TicketVisitorSourceHourSummaryService ticketVisitorSourceHourSummaryService;

    @Autowired
    private TicketChannelHourSummaryService ticketChannelHourSummaryService;

    @Autowired
    private TicketModelHourSummaryService ticketModelHourSummaryService;

    @Override
    public List<SaleTrendVO> querySaleTrend(TicketSumaryQuery query) {
        return ticketHourSummaryService.getBaseMapper().queryTrend(query);
    }

    @Override
    public List<SaleTrendVO> queryVisitTrend(TicketSumaryQuery query) {
        return ticketHourSummaryService.getBaseMapper().queryTrend(query);
    }

    @Override
    public JSONObject querySaleQuantity(TicketSaleQuantityQuery query) {
        return ticketHourSummaryService.querySaleQuantity();
    }

    @Override
    public VisitorSourceInfo queryVisitorSource(TicketSumaryQuery query) {
        return ticketVisitorSourceHourSummaryService.queryVisitorSourceTypeInfo(query);
    }

    @Override
    public IPage<TicketRankingVO> queryVisitorSourceRankingByProvince(TicketRankingQuery query) {
        return ticketVisitorSourceHourSummaryService.getBaseMapper().queryVisitorSourceRankingByProvince(
                new Page<>(query.getPageNo(), query.getPageSize()), query);
    }

    @Override
    public IPage<TicketRankingVO> queryVisitorSourceRankingByCity(TicketRankingQuery query) {
        return ticketVisitorSourceHourSummaryService.getBaseMapper().queryVisitorSourceRankingByCity(
                new Page<>(query.getPageNo(), query.getPageSize()), query);
    }

    @Override
    public IPage<TicketRankingVO> queryTicketChannelRanking(TicketRankingQuery query) {
        return ticketChannelHourSummaryService.getBaseMapper().queryTicketChannelRanking(
                new Page<>(query.getPageNo(), query.getPageSize()), query);
    }

    @Override
    public IPage<TicketRankingVO> queryTicketModelRanking(TicketRankingQuery query) {
        return ticketModelHourSummaryService.getBaseMapper().queryTicketModelRanking(
                new Page<>(query.getPageNo(), query.getPageSize()), query);
    }

    @Override
    public List<BaseVO> queryFareCollection(TicketSaleQuantityQuery query) {
        return ticketHourSummaryService.queryFareCollection();
    }
}
