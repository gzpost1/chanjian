package com.yjtech.wisdom.tourism.message.admin.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息中心
 *
 * @author renguangqian
 * @date 2021/7/23 10:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@TableName("tb_message_center")
public class MessageEntity implements Serializable {

    private static final long serialVersionUID = 3063663633871165100L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 事件Id
     */
    private Long eventId;

    /**
     * 事件名称
     */
    private String eventName;

    /**
     * 事件类型 0:旅游投诉 1:应急事件
     */
    private Integer eventType;

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
    private Long eventHappenPersonId;

    /**
     * 上报人/投诉人 联系电话
     */
    private String eventHappenPersonPhone;

    /**
     * 事件/投诉 处理人Id
     */
    private String eventDealPersonId;

    /**
     * 事件处理跳转的 URL
     */
    private String eventDealUrl;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 修改人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * 是否删除, 0:否, 1:是
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    @JsonIgnore
    private Byte deleted;


}
