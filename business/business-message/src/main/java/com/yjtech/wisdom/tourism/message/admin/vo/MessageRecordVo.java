package com.yjtech.wisdom.tourism.message.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 消息推送记录
 *
 * @author renguangqian
 * @date 2021/7/26 19:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MessageRecordVo implements Serializable {

    private static final long serialVersionUID = -6640427683464892074L;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 发送方式 0:后台 1:App 2:短信
     */
    private Integer sendType;

    /**
     * 发送对象
     */
    private Integer sendObject;

    /**
     * 事件id
     */
    private Long eventId;

    /**
     * 是否发送成功 0：失败 1：成功
     */
    private Byte success;

    /**
     * 返回消息
     */
    private String response;

}
