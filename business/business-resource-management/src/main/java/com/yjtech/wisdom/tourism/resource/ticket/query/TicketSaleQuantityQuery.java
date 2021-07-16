package com.yjtech.wisdom.tourism.resource.ticket.query;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liuhong
 * @date 2021-07-14 15:19
 */
@Data
public class TicketSaleQuantityQuery implements Serializable {
    private Integer isSimulation = 0;
}
