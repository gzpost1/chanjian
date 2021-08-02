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
     * name 页面名称
     */
    private String name;

    /**
     * temp_id 图表类型
     */
    private String tempName;


}