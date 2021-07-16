package com.yjtech.wisdom.tourism.systemconfig.chart.dto;

import com.yjtech.wisdom.tourism.common.core.domain.PageQuery;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 李波
 * @description: 页面查询dto
 * @date 2021/7/214:09
 */
@Data
public class SystemConfigChartQueryPageDto extends PageQuery {
    /**
     * 图标名称
     */
    private String chartName;

    /**
     * 大屏菜单类型名称
     */
    private String menuName;

    /**
     * 大屏菜单类型
     */
    @NotBlank(message = "大屏菜单类型不能为空")
    private String menuType;
}
