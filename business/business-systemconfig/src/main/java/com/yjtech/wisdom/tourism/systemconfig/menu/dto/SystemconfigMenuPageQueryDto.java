package com.yjtech.wisdom.tourism.systemconfig.menu.dto;

import com.yjtech.wisdom.tourism.common.core.domain.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统配置-大屏菜单配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemconfigMenuPageQueryDto extends PageQuery {

    /**
     * name 大屏显示名称
     */
    private String name;

    /**
     * menu_type 大屏菜单类型
     */
    private String menuName;

    /**
     * temp_id 模板名称
     */
    private String tempName;

    /**
     * is_show 是否展示(0:否,1:是)
     */
    private String isShow;
}