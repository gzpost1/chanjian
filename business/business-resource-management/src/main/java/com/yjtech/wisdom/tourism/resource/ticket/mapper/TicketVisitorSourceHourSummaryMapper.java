package com.yjtech.wisdom.tourism.resource.ticket.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.mybatis.extension.MyBaseMapper;
import com.yjtech.wisdom.tourism.resource.ticket.entity.TicketVisitorSourceHourSummary;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketRankingQuery;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketSummaryQuery;
import com.yjtech.wisdom.tourism.resource.ticket.vo.TicketRankingVO;
import com.yjtech.wisdom.tourism.resource.ticket.vo.TicketSourceSummaryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-04
 */
public interface TicketVisitorSourceHourSummaryMapper extends MyBaseMapper<TicketVisitorSourceHourSummary> {


    List<TicketSourceSummaryVo> sourceType(@Param("params") TicketSummaryQuery query);

    IPage<TicketRankingVO> queryVisitorSourceRankingByProvince(IPage<TicketRankingVO> page, @Param("params") TicketRankingQuery query);

    IPage<TicketRankingVO> queryVisitorSourceRankingByCity(IPage<TicketRankingVO> page,@Param("params") TicketRankingQuery query);
}
