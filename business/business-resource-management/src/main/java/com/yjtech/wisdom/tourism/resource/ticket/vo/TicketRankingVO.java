package com.yjtech.wisdom.tourism.resource.ticket.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuhong
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketRankingVO {
    /* 名称 */
    private String name;

    /* 数量 */
    private int value;
}
