package com.yjtech.wisdom.tourism.systemconfig.temp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 系统配置-大屏模板库-修改
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemconfigTempUpdateDto extends SystemconfigTempCreateDto{

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;
}