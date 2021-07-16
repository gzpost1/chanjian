package com.yjtech.wisdom.tourism.resource.ticket.extensionpoint;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.extension.ExtensionPointI;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketRankingQuery;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketSaleQuantityQuery;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketSumaryQuery;
import com.yjtech.wisdom.tourism.resource.ticket.vo.SaleTrendVO;
import com.yjtech.wisdom.tourism.resource.ticket.vo.TicketRankingVO;
import com.yjtech.wisdom.tourism.resource.ticket.vo.VisitorSourceInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 定义扩展点
 *
 * @author liuhong
 * @date 2021-07-13 11:08
 */
public interface TicketQryExtPt extends ExtensionPointI {
    /**
     * 售票趋势
     * @param query
     * @return
     */
    List<SaleTrendVO> querySaleTrend(TicketSumaryQuery query);

    /**
     * 检票趋势
     * @param query
     * @return
     */
    List<SaleTrendVO> queryVisitTrend(TicketSumaryQuery query);

    /**
     * 售票数量统计
     * @param query
     * @return
     */
    JSONObject querySaleQuantity(TicketSaleQuantityQuery query);

    /**
     * 来源地分布
     *
     * @param query
     * @return
     */
    VisitorSourceInfo queryVisitorSource(TicketSumaryQuery query);

    /**
     * 游客来源省份排名
     *
     * @param query
     * @return
     */
    IPage<TicketRankingVO> queryVisitorSourceRankingByProvince(TicketRankingQuery query);

    /**
     * 游客来源地市排名
     *
     * @param query
     * @return
     */
    IPage<TicketRankingVO> queryVisitorSourceRankingByCity(TicketRankingQuery query);

    /**
     * 售票渠道排名
     *
     * @param query
     * @return
     */
    IPage<TicketRankingVO> queryTicketChannelRanking(TicketRankingQuery query);

    /**
     * 票种排名
     *
     * @param query
     * @return
     */
    IPage<TicketRankingVO> queryTicketModelRanking(TicketRankingQuery query);

    /**
     * 今日售票
     * @return
     */
    List<BaseVO> queryFareCollection(TicketSaleQuantityQuery query);
}
