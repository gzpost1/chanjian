package com.yjtech.wisdom.tourism.chat.dto;

import lombok.Data;

/**
 * @author han
 * @createTime 2022/5/12 16:56
 * @description
 */
@Data
public class SendMessage {

    /**
     *  1-两者见对话
     *  2-对话列表
     */
    private Integer messageType;
    /**
     * 发送者id
     */
    private Long fromId;
    /**
     * 发送方Name
     */
    private String fromName;
    /**
     * 发送的数据
     */
    private Object message;
}
