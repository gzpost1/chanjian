package com.yjtech.wisdom.tourism.project.dto;

import com.yjtech.wisdom.tourism.common.validator.EnumValue;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProjectUpdateStatusParam {
    @NotNull(message = "id不能为空")
    private Long id;

    @NotNull(message = "状态不能为空")
    private Byte status;
}
