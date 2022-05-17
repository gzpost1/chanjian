package com.yjtech.wisdom.tourism.chat.dto;

import lombok.Data;

/**
 * @author han
 * @createTime 2022/5/12 16:03
 * @description
 */
@Data
public class Message {

    /**
     * 发送人
     */
    private Long fromId;
    /**
     * 接收人
     */
    private Long toId;
    /**
     * 发送内容
     */
    private String content;
}
