package com.yjtech.wisdom.tourism.resource.depot.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DepotBaseDto {
    /**
     * 名称
     */
    private String name;
    /**
     * 数量
     */
    private Integer quantity;
}
