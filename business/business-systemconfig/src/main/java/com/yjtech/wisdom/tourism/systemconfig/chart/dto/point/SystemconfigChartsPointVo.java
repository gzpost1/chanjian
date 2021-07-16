package com.yjtech.wisdom.tourism.systemconfig.chart.dto.point;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统配置-图标库-点位配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemconfigChartsPointVo {

    /**
     * chart_id 图表id
     */
    @NotNull(message = "图表id不能为空")
    private Long chartId;

    /**
     * sort_num 展示序号
     */
    @NotNull(message = "展示序号不能为空")
    @Min(value = 0,message = "展示序号最小为0")
    @Max(value = 999,message = "展示序号最大为999")
    private Integer sortNumd;

    /**
     * point_type 点位类型(字典 config_spot_type)
     */
    @NotBlank(message = "点位类型不能为空")
    private String pointType;

    /**
     * name 大屏显示名称
     */
    @NotBlank(message = "大屏显示名称不能为空")
    @Length(max = 15,message = "大屏显示名称最长为15位")
    private String name;

    /**
     * is_show 是否展示(0:否,1:是)
     */
    @NotNull(message = "是否展示不能为空")
    private Byte isShow = 1;

}