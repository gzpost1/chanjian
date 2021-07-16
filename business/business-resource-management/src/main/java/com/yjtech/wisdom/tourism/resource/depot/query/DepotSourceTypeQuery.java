package com.yjtech.wisdom.tourism.resource.depot.query;

import com.yjtech.wisdom.tourism.common.validator.EnumValue;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DepotSourceTypeQuery {

    /**
     * 区域类型 来源 0-市 1-省
     */
    @NotNull(message = "区域类型不能为空")
    @EnumValue(values = {"1", "0"}, message = "区域类型必须位0或1")
    private Integer areaType;
}
