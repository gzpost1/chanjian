package com.yjtech.wisdom.tourism.systemconfig.architecture.dto;

import com.yjtech.wisdom.tourism.common.core.domain.PageQuery;
import lombok.Data;

/**
 * 系统配置-大屏菜单架构
 */
@Data
public class SystemconfigArchitecturePageQuery extends PageQuery {
    /**
     * 点击左侧树结构的id
     */
    private Long menuId ;

    /**
     * 大屏菜单名称
     */
    private String menuName;

    /**
     * 页面名称
     */
    private String pageName;

    /**
     * 是否展示 0否 1是
     */
    private Boolean isShow;

    /**
     * 拼接的sql前端不用传
     */
    private String querySql;
}