package com.yjtech.wisdom.tourism.systemconfig.chart.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 系统配置-图标库-点位配置 
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_systemconfig_charts_point")
public class SystemconfigChartsPointEntity extends BaseEntity {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * chart_id 图表id
     */
    @TableField(value = "chart_id")
    private Long chartId;

    /**
     * sort_num 展示序号
     */
    @TableField(value = "sort_numd")
    private Integer sortNumd;

    /**
     * point_type 点位类型(字典 config_spot_type)
     */
    @TableField(value = "point_type")
    private String pointType;

    /**
     * name 大屏显示名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * is_show 是否展示(0:否,1:是)
     */
    @TableField(value = "is_show")
    private Byte isShow;

}