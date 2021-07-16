package com.yjtech.wisdom.tourism.resource.comment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;

import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
    * 资源管理-口碑详情
    */
@Getter
@Setter
@ToString
@NoArgsConstructor
@TableName(value = "tb_praise_comment")
public class PraiseCommentEntity extends BaseEntity {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 第三方id
     */
    @TableField(value = "comment_id")
    private String commentId;

    /**
     * OTA平台,{ctrip:携程,meituan:美团,qunar:去哪儿,fliggy:''飞猪''}
     */
    @TableField(value = "ota")
    private String ota;

    /**
     * 景区名称
     */
    @TableField(value = "scenic")
    private String scenic;

    /**
     * 用户昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 用户头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 好中差(0好评 1 中评 2差评)
     */
    @TableField(value = "comment_type")
    private Boolean commentType;

    /**
     * 用户评分
     */
    @TableField(value = "score")
    private String score;

    /**
     * 评论标签
     */
    @TableField(value = "tag")
    private String tag;

    /**
     * 评论正文
     */
    @TableField(value = "`text`")
    private String text;

    /**
     * 票务信息
     */
    @TableField(value = "ticket")
    private String ticket;

    /**
     * 门票价格
     */
    @TableField(value = "ticket_price")
    private BigDecimal ticketPrice;

    /**
     * 评价时间
     */
    @TableField(value = "comment_time")
    private Date commentTime;

}