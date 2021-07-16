package com.yjtech.wisdom.tourism.resource.comment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 资源管理-ota每日好中差统计
    */
@Data
@NoArgsConstructor
@TableName(value = "tb_praise_ota_summary")
public class PraiseOtaSummaryEntity extends BaseEntity {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 评价时间
     */
    @TableField(value = "comment_time")
    private Date commentTime;

    /**
     * 渠道名称
     */
    @TableField(value = "ota_type")
    private String otaType;

    /**
     * 差评总数
     */
    @TableField(value = "comment_poor")
    private Long commentPoor;

    /**
     * 中评总数
     */
    @TableField(value = "comment_medium")
    private Long commentMedium;

    /**
     * 好评总数
     */
    @TableField(value = "comment_excellent")
    private Long commentExcellent;

    /**
     * 评价总数
     */
    @TableField(value = "comment_total")
    private Long commentTotal;
}