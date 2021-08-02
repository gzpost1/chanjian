package com.yjtech.wisdom.tourism.message.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder(toBuilder = true)
public class MessageDto implements Serializable {

    private static final long serialVersionUID = 4320572242507254725L;

    /**
     * id
     */
    private Long id;

    /**
     * 事件Id
     */
    private String eventId;

    /**
     * 事件名称
     */
    private String eventName;

    /**
     * 事件类型 0:旅游投诉 1:应急事件
     */
    private Byte eventType;

    /**
     * 事件业务类型 由各业务模块自行定义传输，暂定中文传输，只做前端展示
     */
    private String eventBusinessTypeText;

    /**
     * 事件状态 0:待指派 1:待处理 2:已处理
     */
    private Integer eventStatus;

    /**
     * 事件发生日期/投诉时间
     */
    private String eventHappenDate;

    /**
     * 事件发生地址
     */
    private String eventHappenAddress;

    /**
     * 上报人/投诉人姓名
     */
    private String eventHappenPerson;

    /**
     * 上报人/投诉人 用户id
     */
    private String eventHappenPersonId;

    /**
     * 上报人/投诉人 联系电话
     */
    private String eventHappenPersonPhone;

    /**
     * 事件/投诉 处理人Id
     */
    private String eventDealPersonId;

    /**
     * 事件/投诉 处理状态
     */
    private String eventDealStatus;

    /**
     * 事件处理跳转的 URL
     */
    private String eventDealUrl;

    /**
     * 创建时间
     */
    private String createTime;

}
