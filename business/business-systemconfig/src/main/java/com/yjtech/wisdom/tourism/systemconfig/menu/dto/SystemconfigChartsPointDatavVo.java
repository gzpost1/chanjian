package com.yjtech.wisdom.tourism.systemconfig.menu.dto;

import com.yjtech.wisdom.tourism.system.domain.IconDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统配置-图标库-点位配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemconfigChartsPointDatavVo {

    /**
     * chart_id 图表id
     */
    private Long chartId;

    /**
     * sort_num 展示序号
     */
    private Integer sortNumd;

    /**
     * point_type 点位类型(字典 config_spot_type)
     */
    private String pointType;

    /**
     * name 大屏显示名称
     */
    private String name;

    /**
     * 图标明细
     */
    private List<IconDetail> value = new ArrayList<>();
}