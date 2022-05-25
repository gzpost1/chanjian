package com.yjtech.wisdom.tourism.chat.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjtech.wisdom.tourism.common.annotation.Excel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author han
 * @createTime 2022/5/12 17:25
 * @description
 */
@Data
public class ChatMessageExportVo {

    @Excel(name = "企业名称")
    private String fromName;

    /**
     * 发送内容
     */
    @Excel(name = "消息内容")
    private String content;

    /**
     * 发送时间
     */
    @Excel(name = "消息时间")
    private String sendTime;


}
