package com.yjtech.wisdom.tourism.resource.comment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 口碑-评论热词，行业评价分布
 */
@Data
@NoArgsConstructor
@TableName(value = "tb_praise_word_summary")
public class PraiseWordSummaryEntity extends BaseEntity {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 结果
     */
    @TableField(value = "`result`")
    private String result;

    /**
     * 1评论热词  2行业评价分布
     */
    @TableField(value = "`type`")
    private Byte type;
}