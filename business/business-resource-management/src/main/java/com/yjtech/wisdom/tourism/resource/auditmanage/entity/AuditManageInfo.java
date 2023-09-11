package com.yjtech.wisdom.tourism.resource.auditmanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import java.util.Date;
import java.util.List;

import com.yjtech.wisdom.tourism.mybatis.typehandler.ListLongJsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author songjun
 * @since 2023/9/8
 */
/**
    * 审核状态表
    */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "tb_audit_manage_info")
public class AuditManageInfo extends BaseEntity {
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
     * 下级审核人员
     */
    @TableField(value = "next_audit_user_ids",typeHandler = ListLongJsonTypeHandler.class)
    private List<Long> nextAuditUserIds;

    /**
     * 审核状态 0-待审核 1-通过 2-不通过
     */
    @TableField(value = "`status`")
    private Integer status;
}