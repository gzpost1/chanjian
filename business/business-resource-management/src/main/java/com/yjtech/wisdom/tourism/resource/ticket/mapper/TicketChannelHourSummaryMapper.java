package com.yjtech.wisdom.tourism.resource.ticket.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.mybatis.extension.MyBaseMapper;
import com.yjtech.wisdom.tourism.resource.ticket.entity.TicketChannelHourSummaryEntity;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketRankingQuery;
import com.yjtech.wisdom.tourism.resource.ticket.vo.TicketRankingVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-04
 */
public interface TicketChannelHourSummaryMapper extends MyBaseMapper<TicketChannelHourSummaryEntity> {

    IPage<TicketRankingVO> queryTicketChannelRanking(IPage<TicketRankingVO> page, @Param("params") TicketRankingQuery query);

}
