package com.yjtech.wisdom.tourism.systemconfig.chart.dto.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统配置-图标库-查询列表字段配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemconfigChartsListQueryVo {

    /**
     * chart_id 图表id
     */
    @NotNull(message = "图表id不能为空")
    private Long chartId;

    /**
     * 列表字段所需键值
     */
    @NotBlank(message = "列表字段所需键值为空")
    private String listType;
}