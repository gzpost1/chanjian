package com.yjtech.wisdom.tourism.resource.ticket.vo;

import lombok.Data;

/**
 * @author xulei
 * @create 2021-07-03 14:41
 */
@Data
public class TicketFareCollectionVO{
    /**
     * 应入园团队游客人数
     */
    private Integer willVisitGroupQuantity;

    /**
     * 实际入园团队游客数
     */
    private Integer groupQuantity;

    /**
     * 应入园散客数
     */
    private Integer willVisitIndividualQuantity;

    /**
     * 实际入园散客数
     */
    private Integer individualQuantity;
}
