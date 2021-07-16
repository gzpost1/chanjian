package com.yjtech.wisdom.tourism.resource.ticket.extensionpoint.mock;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.resource.ticket.extensionpoint.TicketExtensionConstant;
import com.yjtech.wisdom.tourism.resource.ticket.extensionpoint.TicketQryExtPt;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketRankingQuery;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketSaleQuantityQuery;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketSumaryQuery;
import com.yjtech.wisdom.tourism.resource.ticket.vo.SaleTrendVO;
import com.yjtech.wisdom.tourism.resource.ticket.vo.TicketRankingVO;
import com.yjtech.wisdom.tourism.resource.ticket.vo.VisitorSourceInfo;

import java.util.List;

/**
 * 票务模拟数据扩展点
 *
 * @author liuhong
 * @date 2021-07-13 11:12
 */
@Extension(bizId = ExtensionConstant.BIZ_TICKET,
        useCase = TicketExtensionConstant.USE_CASE_STATISTIC,
        scenario = ExtensionConstant.SCENARIO_MOCK)
public class MockTicketQryExt implements TicketQryExtPt {

    @Override
    public List<SaleTrendVO> querySaleTrend(TicketSumaryQuery query) {
        return null;
    }

    @Override
    public List<SaleTrendVO> queryVisitTrend(TicketSumaryQuery query) {
        return null;
    }

    @Override
    public JSONObject querySaleQuantity(TicketSaleQuantityQuery query) {
        return null;
    }

    @Override
    public VisitorSourceInfo queryVisitorSource(TicketSumaryQuery query) {
        return null;
    }

    @Override
    public IPage<TicketRankingVO> queryVisitorSourceRankingByProvince(TicketRankingQuery query) {
        return null;
    }

    @Override
    public IPage<TicketRankingVO> queryVisitorSourceRankingByCity(TicketRankingQuery query) {
        return null;
    }

    @Override
    public IPage<TicketRankingVO> queryTicketChannelRanking(TicketRankingQuery query) {
        return null;
    }

    @Override
    public IPage<TicketRankingVO> queryTicketModelRanking(TicketRankingQuery query) {
        return null;
    }

    @Override
    public List<BaseVO> queryFareCollection(TicketSaleQuantityQuery query) {
        return null;
    }
}
