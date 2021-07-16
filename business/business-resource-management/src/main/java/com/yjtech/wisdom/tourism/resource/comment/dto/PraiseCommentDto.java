package com.yjtech.wisdom.tourism.resource.comment.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资源管理-口碑详情
 */
@Data
public class PraiseCommentDto {
    /**
     * id
     */
    private Long id;

    /**
     * 第三方id
     */
    private String commentId;

    /**
     * 评价渠道   OTA平台,{ctrip:携程,meituan:美团,qunar:去哪儿,fliggy:''飞猪''}
     */
    private String ota;

    /**
     * 景区名称
     */
    private String scenic;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 好中差(0好评 1 中评 2差评)
     */
    private Byte commentType;

    /**
     * 用户评分
     */
    private String score;

    /**
     * 评论标签
     */
    private String tag;

    /**
     * 评论正文
     */
    private String text;

    /**
     * 票务信息
     */
    private String ticket;

    /**
     * 门票价格
     */
    private BigDecimal ticketPrice;

    /**
     * 评价时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date commentTime;

}