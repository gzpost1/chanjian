package com.yjtech.wisdom.tourism.resource.broadcast.query;

import com.yjtech.wisdom.tourism.common.validator.EnumValue;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zc
 */
@Data
public class EquipmentTypeQuery {

    @NotNull(message = "类型不能为空")
    @EnumValue(values = {"0", "1","2"})
    private Integer type;
}
