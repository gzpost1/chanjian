package com.yjtech.wisdom.tourism.resource.ticket.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketSourceSummaryVo implements Serializable {

    /**
     * 来源,0:本地,1:省内外地,2:省外
     */
    private Byte sourceType;

    /**
     * 来源省
     */
    private String province;

    /**
     * 来源市
     */
    private String city;

    /**
     * 数量
     */
    private Integer quantity;

}