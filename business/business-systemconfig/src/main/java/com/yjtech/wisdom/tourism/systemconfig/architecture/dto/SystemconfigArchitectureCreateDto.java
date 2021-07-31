package com.yjtech.wisdom.tourism.systemconfig.architecture.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统配置-大屏菜单架构
 */
@Data
public class SystemconfigArchitectureCreateDto {
    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    @Length(max = 10,message = "菜单名称最多10个字符")
    private String menuName;

    /**
     * 父菜单ID  如果初始提交，parentId为0
     */
    @NotNull(message = "父菜单id不能为空")
    private Long parentId;

    /**
     * 页面id
     */
    private Long pageId;
}