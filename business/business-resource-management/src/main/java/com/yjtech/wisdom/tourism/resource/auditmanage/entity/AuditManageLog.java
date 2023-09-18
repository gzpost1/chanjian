package com.yjtech.wisdom.tourism.resource.auditmanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author songjun
 * @since 2023/9/18
 */

/**
 * 审核记录表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_audit_manage_log")
public class AuditManageLog extends BaseEntity {
    /**
     * 主键，序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 流程id
     */
    @TableField(value = "process_id")
    private Long processId;

    /**
     * 项目id
     */
    @TableField(value = "source_id")
    private Long sourceId;

    /**
     * 审核意见
     */
    @TableField(value = "`text`")
    private String text;

    /**
     * 审核状态 0-待审核 1-通过 2-不通过
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 审核用户，创建用户是上一步的用户
     */
    @TableField(value = "audit_user")
    private Long auditUser;
    /**
     * 操作人
     */
    @TableField(exist = false)
    private String username;
}