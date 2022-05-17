package com.yjtech.wisdom.tourism.chat.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author han
 * @createTime 2022/5/13 9:22
 * @description
 */
@Data
public class EnterpriseVo {

    private Long id;

    private Long sessionId;

    private String name;

    /**
     *  是否有未读消息
     */
    private String hasUnread;

    /**
     * 最新聊天时间
     */
    private Date lastChatTime;
}
