package com.yjtech.wisdom.tourism.resource.depot.query;

import com.yjtech.wisdom.tourism.common.validator.EnumValue;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DepotTimeTypeQuery{

    /**时间类型（1-年 2-月 3-周  4-日）*/
    @NotNull(message = "时间类型不能为空")
    @EnumValue(values = {"1","2","3","4"}, message = "时间类型只能在1~4之间")
    private Integer timeType;
}
