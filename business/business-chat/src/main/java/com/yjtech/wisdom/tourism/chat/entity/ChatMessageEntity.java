package com.yjtech.wisdom.tourism.chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuyongchong
 * @since 2022-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("chat_message")
@Builder
public class ChatMessageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 发送人
     */
    private Long fromUserId;

    /**
     * 接收人
     */
    private Long toUserId;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 发送内容
     */
    private String content;

    /**
     * Y-已读，N-未读
     */
    private String readStatus;

    /**
     * Y-已删，N-未删
     */
    private String logDel;

    /**
     * 对会话用户进行md5加密
     */
    private String md5UserId;

}
