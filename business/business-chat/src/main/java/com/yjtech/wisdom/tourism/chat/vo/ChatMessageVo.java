package com.yjtech.wisdom.tourism.chat.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjtech.wisdom.tourism.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @author han
 * @createTime 2022/5/12 17:25
 * @description
 */
@Data
public class ChatMessageVo {
    /**
     * 发送人
     */
    private Long fromUserId;

    @Excel(name = "企业名称")
    private String fromName;

    /**
     * 接收人
     */
    private Long toUserId;

    private String toName;

    /**
     * 发送时间
     */
    @Excel(name = "消息时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sendTime;

    /**
     * 发送内容
     */
    @Excel(name = "消息内容")
    private String content;
}
