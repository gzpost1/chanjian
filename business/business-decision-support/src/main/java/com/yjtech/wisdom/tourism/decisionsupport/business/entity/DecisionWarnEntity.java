package com.yjtech.wisdom.tourism.decisionsupport.business.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 决策预警数据
 *
 * @author renguangqian
 * @date 2021/7/28 20:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@TableName("tb_decision_warn")
public class DecisionWarnEntity implements Serializable {

    private static final long serialVersionUID = 803197240906158093L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 指标id
     */
    private Long targetId;

    /**
     * 指标名称
     */
    private String targetName;

    /**
     * 预警项名称
     */
    private String warnName;

    /**
     * 预警项值
     */
    private String warnNum;

    /**
     * 报警类型-文本
     */
    private String alarmTypeText;

    /**
     * 报警类型
     */
    private Integer alarmType;

    /**
     * 月环比
     */
    private String monthHbScale;

    /**
     * 风险类型（等级） 0：低风险 1：中风险 2：高风险
     */
    private Integer riskType;

    /**
     * 话术
     */
    private String conclusionText;

    /**
     * 是否使用缺失话术 0：不使用 1：使用
     */
    @TableField(fill = FieldFill.INSERT)
    private Byte isUseMissConclusionText;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private Date updateTime;

    /**
     * 创建人ID
     */
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 修改人ID
     */
    @JsonIgnore
    @TableField(fill = FieldFill.UPDATE)
    private Long updateUser;

}
