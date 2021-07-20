package com.yjtech.wisdom.tourism.resource.ticket.query;

import com.yjtech.wisdom.tourism.common.bean.TimeBaseQuery;
import com.yjtech.wisdom.tourism.extension.BizScenario;
import lombok.Data;

/**
 * @author xulei
 * @create 2021-07-03 14:26
 */
@Data
public class TicketSumaryQuery extends TimeBaseQuery {
    private Integer isSimulation = 0;

    private BizScenario bizScenario;
}