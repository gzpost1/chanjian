package com.yjtech.wisdom.tourism.common.core.domain;

import com.yjtech.wisdom.tourism.common.validator.EnumValue;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @Description
 * @Author 陈忠凌
 * @Date 2020-08-18 10:37
 */
@Setter
@Getter
public class UpdateStatusParam {
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 状态 0否 1是
     */
    @EnumValue(values = {"1", "0"},message = "状态必须为1或0")
    @NotNull(message = "状态不能为空")
    private Byte status;
}
