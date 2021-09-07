package com.yjtech.wisdom.tourism.message.admin.dto;

import com.yjtech.wisdom.tourism.common.sms.MessageCallDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 消息中心
 *
 * @author renguangqian
 * @date 2021/7/26 9:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto extends MessageCallDto implements Serializable {

    private static final long serialVersionUID = 4320572242507254725L;

    /**
     * id
     */
    private Long id;

    /**
     * 事件类型 0:旅游投诉 1:应急事件
     */
    private Byte eventType;

    /**
     * 事件/投诉 处理人Id
     */
    private String eventDealPersonId;

    /**
     * 消息标题
     */
    private String title;

}
