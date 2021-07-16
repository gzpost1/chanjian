package com.yjtech.wisdom.tourism.systemconfig.chart.dto.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统配置-图标库-列表字段配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemconfigChartsListVo {

    /**
     * chart_id 图表id
     */
    private Long chartId;

    /**
     * filed_key 字典中字段的键
     */
    private String filedKey;

    /**
     * is_show 是否展示(0:否,1:是)
     */
    private Byte isShow = 1;

}