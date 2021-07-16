package com.yjtech.wisdom.tourism.systemconfig.simulation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName(value = "tb_simulation_config")
public class SimulationConfigEntity extends BaseEntity {
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 1票务 2停车场 3wifi 4一码游 5智慧厕所 6口碑 7事件
     */
    @TableField(value = "domain_id")
    private Integer domainId;

    /**
     * 配置JSON
     */
    @TableField(value = "config_data")
    private String configData;
}