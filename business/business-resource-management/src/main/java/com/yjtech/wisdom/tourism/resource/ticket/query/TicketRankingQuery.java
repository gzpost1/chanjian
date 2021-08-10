package com.yjtech.wisdom.tourism.resource.ticket.query;

import com.yjtech.wisdom.tourism.mybatis.entity.TimeBaseQuery;
import lombok.Data;

/**
 * @author xulei
 * @create 2021-07-03 14:26
 */
@Data
public class TicketRankingQuery extends TimeBaseQuery {
    private Integer isSimulation = 0;


}
