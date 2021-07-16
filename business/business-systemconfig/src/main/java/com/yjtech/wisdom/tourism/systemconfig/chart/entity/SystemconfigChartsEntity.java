package com.yjtech.wisdom.tourism.systemconfig.chart.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统配置-图标库
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_systemconfig_charts")
public class SystemconfigChartsEntity extends BaseEntity {

    /** id */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * name 图标名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * chart_type 图标类型
     */
    @TableField(value = "chart_type")
    private String chartType;

    /**
     * menu_type 大屏菜单类型
     */
    @TableField(value = "menu_type")
    private String menuType;

    /**
     * service_url 后端服务url
     */
    @TableField(value = "service_url")
    private String serviceUrl;

    /**
     * commponent_type 组件类型
     */
    @TableField(value = "commponent_type")
    private String commponentType;

    /**
     * list_type 当图表类型选择列表时显示
     */
    @TableField(value = "list_type",updateStrategy = FieldStrategy.IGNORED)
    private String listType;

    /**
     * is_simulation 是否有模拟数据 1有 0无
     */
    @TableField(value = "is_simulation")
    private Byte isSimulation;

    /**
     * sample_img 示例图
     */
    @TableField(value = "sample_img")
    private String sampleImg;
}