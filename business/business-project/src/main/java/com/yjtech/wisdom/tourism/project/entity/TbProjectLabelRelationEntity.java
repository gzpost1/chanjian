package com.yjtech.wisdom.tourism.project.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目-标签关系(TbProjectLabelRelation)实体类
 *
 * @author horadirm
 * @since 2022-05-18 18:43:50
 */
@Data
@TableName(value = "tb_project_label_relation")
@NoArgsConstructor
@AllArgsConstructor
public class TbProjectLabelRelationEntity implements Serializable {

    private static final long serialVersionUID = -88994039916734659L;

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 标签id
     */
    private Long labelId;


}