package com.yjtech.wisdom.tourism.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yjtech.wisdom.tourism.common.annotation.ExcelRead;
import com.yjtech.wisdom.tourism.common.serializer.LongTypeJsonSerializer;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 项目信息表-资源
 */
@Data
@ToString
@TableName(value = "tb_project_resource")
public class TbProjectResourceEntity extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonSerialize(using = LongTypeJsonSerializer.class)
    private Long id;

    /**
     * 资源名称
     */
    @NotBlank(message = "资源名称不能为空")
    @Length(max = 30, message = "资源名称最长30位")
    @TableField(value = "`name`")
    private String name;

    /**
     * 资源url
     */
    @NotBlank(message = "资源url不能为空")
    @TableField(value = "resource_url")
    private String resourceUrl;

    /**
     * 封面
     */
    @TableField(value = "index_url")
    private String indexUrl;

    /**
     * 项目id
     */
    @NotNull(message = "项目id不能为空")
    @TableField(value = "project_id")
    private Long projectId;

    /**
     * 资源类型 0vr  1视屏 2ppt
     */
    @NotNull(message = "资源类型不能为空")
    @TableField(value = "resource_type")
    private Byte resourceType;
}