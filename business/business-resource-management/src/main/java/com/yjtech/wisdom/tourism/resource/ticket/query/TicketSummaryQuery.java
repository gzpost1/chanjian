package com.yjtech.wisdom.tourism.resource.ticket.query;

import com.yjtech.wisdom.tourism.extension.BizScenario;
import com.yjtech.wisdom.tourism.mybatis.entity.TimeBaseQuery;
import lombok.Data;

/**
 * @author xulei
 * @create 2021-07-03 14:26
 */
@Data
public class TicketSummaryQuery extends TimeBaseQuery {
    private Integer isSimulation = 0;

    private BizScenario bizScenario;
}
