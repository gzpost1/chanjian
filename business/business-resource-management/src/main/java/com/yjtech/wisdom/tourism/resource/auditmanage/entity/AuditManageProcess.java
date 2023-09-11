package com.yjtech.wisdom.tourism.resource.auditmanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import com.yjtech.wisdom.tourism.mybatis.typehandler.ListLongJsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author songjun
 * @since 2023/9/8
 */

/**
 * 审核流程表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_audit_manage_process", autoResultMap = true)
public class AuditManageProcess extends BaseEntity {
    /**
     * 主键，序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 配置id
     */
    @TableField(value = "config_id")
    private Long configId;

    /**
     * 审核层级
     */
    @TableField(value = "sort")
    @JsonIgnore
    private Integer sort;

    /**
     * 审核人员
     */
    @TableField(value = "user_ids", typeHandler = ListLongJsonTypeHandler.class)
    @NotEmpty(message = "审核人员不能为空")
    private List<Long> userIds;

    /**
     * 审核人员名字
     */
    @TableField(exist = false)
    private List<String> userNames;
}