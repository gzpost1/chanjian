package com.yjtech.wisdom.tourism.systemconfig.architecture.dto;

import lombok.Data;

/**
 * 系统配置-大屏菜单架构
 */
@Data
public class SystemconfigArchitectureDto {
    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 页面id
     */
    private Long pageId;

    /**
     * 页面名称
     */
    private String pageName;

    /**
     * 是否展示 0否 1是
     */
    private Byte isShow;

    /**
     * 是否启用模拟数据(0:否,1:是)
     */
    private Byte isSimulation;

    /**
     * 序号
     */
    private Integer sortNum;

    /**
     * img_url 大屏缩略图
     */
    private String imgUrl;
}