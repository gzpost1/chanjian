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
public class CityInfo {
    /* 地市名 */
    private String city;

    /* 数量 */
    private int quantity;
}
