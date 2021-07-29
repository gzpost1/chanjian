package com.yjtech.wisdom.tourism.decisionsupport.business.entity;

import com.baomidou.mybatisplus.annotation.*;
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
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 修改人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

}
