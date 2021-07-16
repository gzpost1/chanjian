package com.yjtech.wisdom.tourism.resource.depot.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepotBaseAddPercentVo extends DepotBaseVo{

    /**百分比*/
    private Double percent;
}
