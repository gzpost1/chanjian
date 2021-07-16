package com.yjtech.wisdom.tourism.resource.comment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 资源管理-好中差统计
 */
@Data
@NoArgsConstructor
@TableName(value = "tb_praise_type_summary")
public class PraiseTypeSummaryEntity extends BaseEntity {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 评价日期
     */
    @TableField(value = "comment_time")
    private Date commentTime;

    /**
     * 差评总数
     */
    @TableField(value = "comment_poor")
    private Long commentPoor = 0L;

    /**
     * 中评总数
     */
    @TableField(value = "comment_medium")
    private Long commentMedium = 0L;

    /**
     * 好评总数
     */
    @TableField(value = "comment_excellent")
    private Long commentExcellent = 0L;

    /**
     * 评价总数
     */
    @TableField(value = "comment_total")
    private Long commentTotal = 0L;

}