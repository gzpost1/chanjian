package com.yjtech.wisdom.tourism.systemconfig.chart.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统配置-图标库
 */
@Data
public class SystemconfigChartsCreateDto {

    /**
     * name 图表名称
     */
    @NotBlank(message = "图表名称不能为空")
    @Length(max = 10, message = "图表名称必须小于等于10位")
    private String name;

    /**
     * chart_type 图表类型 (字典 config_table_type)
     */
    @NotBlank(message = "图标类型不能为空")
    private String chartType;

    /**
     * menu_type 点位类型
     */
    private String pointType;

    /**
     * menu_type 页面类型（字典 config_menu_type）
     */
    @NotBlank(message = "页面类型不能为空")
    private String menuType;

    /**
     * service_url 后端服务url
     */
    @NotBlank(message = "后端服务url不能为空")
    @Length(max = 256, message = "后端服务url必须小于等于256位")
    private String serviceUrl;

    /**
     * list_type 当图表类型选择列表时显示 字典键值
     */
    @Length(max = 256, message = "字典键值必须小于等于256位")
    private String listType;

    /**
     * commponent_type 组件类型
     */
    @NotBlank(message = "组件类型不能为空")
    private String commponentType;

    /**
     * is_simulation 是否有模拟数据 1有 0无
     */
    @NotNull(message = "是否有模拟数据不能为空")
    private Byte isSimulation = 0;

    /**
     * sample_img 示例图
     */
    @NotBlank(message = "示例图不能为空")
    private String sampleImg;

    /**
     * 是否有页面跳转 0-否,1-是
     */
    @NotNull(message = "是否有页面跳转不能为空")
    private Byte isRedirect;

    /**
     * 跳转页面id
     */
    private String redirectId;

    /**
     * 指标项
     */
    private String indexItem;

}