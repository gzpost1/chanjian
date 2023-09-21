package com.yjtech.wisdom.tourism.resource.auditmanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author songjun
 * @since 2023/9/8
 */

/**
 * 审核配置表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_audit_manage_config")
public class AuditManageConfig extends BaseEntity {
    /**
     * 主键，序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    @TableField(value = "`name`")
    private String name;

    @TableField(exist = false)
    private List<AuditManageProcess> processList;
    @TableField(exist = false)
    private String processStr;

}