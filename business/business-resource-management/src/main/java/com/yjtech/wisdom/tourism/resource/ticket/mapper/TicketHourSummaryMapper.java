package com.yjtech.wisdom.tourism.resource.ticket.mapper;

import com.yjtech.wisdom.tourism.mybatis.extension.MyBaseMapper;
import com.yjtech.wisdom.tourism.resource.ticket.entity.TicketHourSummaryEntity;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketSummaryQuery;
import com.yjtech.wisdom.tourism.resource.ticket.vo.SaleTrendVO;
import com.yjtech.wisdom.tourism.resource.ticket.vo.TicketFareCollectionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulei
 * @since 2021-07-03
 */
public interface TicketHourSummaryMapper extends MyBaseMapper<TicketHourSummaryEntity> {


    /**
     *  趋势统计(包括检票 售票)
     * @param query
     * @return
     */
    List<SaleTrendVO> queryTrend(@Param("params") TicketSummaryQuery query);


    /**
     * 售票数量统计
     * @param query
     * @return
     */
    Integer querySaleQuantity(@Param("params") TicketSummaryQuery query);

    /**
     * 今日售检票
     * @param query
     * @return
     */
    TicketFareCollectionVO queryFareCollection(@Param("params") TicketSummaryQuery query);

    /**
     * 查询入园统计
     * @param params
     * @return
     */
    Long queryVisitStatistics(@Param("params") TicketSummaryQuery params);
}
