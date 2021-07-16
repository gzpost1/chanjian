package com.yjtech.wisdom.tourism.systemconfig.chart.dto.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统配置-图标库-列表字段配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemconfigChartsListCreateDto {

    /**
     * chart_id 图表id
     */
    @NotNull(message = "图表id不能为空")
    private Long chartId;

    /**
     * filed_key 字典中字段的键
     */
    @NotBlank(message = "字典中字段的键不能为空")
    private String filedKey;

    /**
     * is_show 是否展示(0:否,1:是)
     */
    @NotNull(message = "是否展示不能为空")
    private Byte isShow = 1;

}