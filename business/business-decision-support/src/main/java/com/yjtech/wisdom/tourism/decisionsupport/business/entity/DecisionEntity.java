package com.yjtech.wisdom.tourism.decisionsupport.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 决策库
 *
 * @author renguangqian
 * @date 2021/7/28 19:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@TableName("tb_decision_library")
public class DecisionEntity implements Serializable {

    private static final long serialVersionUID = 1972531819469469083L;

    /**
     * 指标id
     */
    @TableId(value = "target_id")
    private Long targetId;

    /**
     * 指标名称
     */
    private String targetName;

    /**
     * 预警配置id
     */
    private Long configId;

    /**
     * 预警配置名称
     */
    private String configName;

    /**
     * 预警配置类型 0：文本 1：数值
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private Integer configType;

    /** ======================================= 数值 配置类型 ======================================= */

    /**
     * 变化类型 0：上升  1：下降 2：忽略变化
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private Integer changeType;

    /**
     * 低风险预警值
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private Double lowRiskThreshold;

    /**
     * 中风险预警值
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private Double mediumRiskThreshold;

    /**
     * 高风险预警值
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private Double highRiskThreshold;

    /** ======================================= 文本 配置类型 ======================================= */

    /**
     * 风险类型 0：低风险 1：中风险 2：高风险 3：无风险
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private Integer riskType;

    /**
     * 话术
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String conclusionText;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(serialize = false)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    @JSONField(serialize = false)
    private Long createUser;

    /**
     * 修改人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JSONField(serialize = false)
    private Long updateUser;

    /**
     * 是否删除, 0:否, 1:是
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonIgnore
    @JSONField(serialize = false)
    private Byte deleted;

}
