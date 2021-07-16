package com.yjtech.wisdom.tourism.systemconfig.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 系统配置-大屏菜单配置-修改
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemconfigMenuUpdateDto extends SystemconfigMenuCreateDto{

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;
}