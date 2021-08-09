package com.yjtech.wisdom.tourism.systemconfig.architecture.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 系统配置-大屏菜单架构
 */
@Data
public class SystemconfigArchitectureUpdateDto extends SystemconfigArchitectureCreateDto{

    /**
     * 架构id
     */
    @NotNull(message = "页面架构id不能为空")
    private Long menuId;
}