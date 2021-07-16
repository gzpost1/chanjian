package com.yjtech.wisdom.tourism.systemconfig.chart.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 系统配置-图标库-列表字段配置 
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_systemconfig_charts_list")
public class SystemconfigChartsListEntity extends BaseEntity {
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
     * filed_key 字典中字段的键
     */
    @TableField(value = "filed_key")
    private String filedKey;

    /**
     * is_show 是否展示(0:否,1:是)
     */
    @TableField(value = "is_show")
    private Byte isShow;

}