package com.yjtech.wisdom.tourism.bigscreen.dto;

import com.yjtech.wisdom.tourism.common.validator.EnumValue;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**企业数据权限*/
@Data
public class DataPermissionsParam {

    @NotNull(message = "id不能为空")
    private Long id;

    @NotBlank(message = "数据权限不能为空")
    @EnumValue(values = {"0", "1"}, message = "状态值0或者1")
    private String dataPermissions;
}
