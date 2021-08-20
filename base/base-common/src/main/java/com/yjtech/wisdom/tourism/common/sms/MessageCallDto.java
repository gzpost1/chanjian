package com.yjtech.wisdom.tourism.common.sms;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 消息中心-通过事件id，查询事件信息dto
 *
 * @author renguangqian
 * @date 2021/8/4 10:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MessageCallDto implements Serializable {

    private static final long serialVersionUID = -3934012389057677087L;

    /**
     * 事件id
     */
    private Long eventId;

    /**
     * 事件名称
     */
    private String eventName;

    /**
     * 事件状态 0:待指派 1:待处理 2:已处理
     *
     * 由于前端已对接，保留字段
     */
    private Integer eventStatus;

    /**
     * 事件状态文本
     */
    private String eventStatusText;

    /**
     * 事件发生日期/投诉时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime eventHappenDate;

    /**
     * 事件业务类型 由各业务模块自行定义传输，暂定中文传输，只做前端展示
     */
    private String eventBusinessTypeText;

    /**
     * 事件发生地址
     */
    private String eventHappenAddress;

    /**
     * 上报人/投诉人姓名
     */
    private String eventHappenPerson;

    /**
     * 事件创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
