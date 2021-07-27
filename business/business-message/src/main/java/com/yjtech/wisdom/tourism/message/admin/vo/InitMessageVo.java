package com.yjtech.wisdom.tourism.message.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 处理
 *
 * @author renguangqian
 * @date 2021/7/26 9:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class InitMessageVo implements Serializable {

    private static final long serialVersionUID = 6812211605569384396L;

    /**
     * 事件Id
     */
    @NotBlank
    private String eventId;

    /**
     * 事件名称
     */
    @NotBlank
    private String eventName;

    /**
     * 事件类型 0:旅游投诉 1:应急事件
     */
    private Byte eventType;

    /**
     * 事件状态 0:待指派 1:待处理 2:已处理
     */
    @NotNull
    private Integer eventStatus;

    /**
     * 事件发生日期/投诉时间
     */
    @NotBlank
    private String eventHappenDate;

    /**
     * 事件发生地址
     */
    @NotBlank
    private String eventHappenAddress;

    /**
     * 上报人/投诉人姓名
     */
    @NotBlank
    private String eventHappenPerson;

    /**
     * 上报人/投诉人 用户id
     */
    @NotNull
    private Long eventHappenPersonId;

    /**
     * 上报人/投诉人 联系电话
     */
    @NotBlank
    private String eventHappenPersonPhone;

    /**
     * 事件/投诉 处理人Id，若有多个，采用逗号“,”分割id
     */
    private Long eventDealPersonId;

    /**
     * 事件处理跳转的 URL
     */
    private String eventDealUrl;
}
