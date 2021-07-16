package com.yjtech.wisdom.tourism.resource.depot.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SingleDepotUseDto {
    /**停车场名称*/
    private String name;

    /**总车位*/
    private Integer value;

    /**已使用停车位*/
    private Integer useValue;

    /**使用率*/
    private Double rate;
}
