package com.yjtech.wisdom.tourism.common.enums;

/**
 * 消息中心-指派状态
 *
 * @author renguangqian
 * @date 2021/7/23 16:10
 */
public enum  MessageAppointStatusEnum {

    /**
     * 管理员
     */
    ADMIN,

    /**
     * 指派回管理员
     */
    TO_ADMIN,

    /**
     * 游客投诉指派人
     */
    TOURIST_COMPLAINTS_ADMIN,

    /**
     * 应急事件的指派人
     */
    EMERGENCY_ADMIN,

    /**
     * 既是游客投诉指派人 又是 应急事件的指派人
     */
    TOURIST_COMPLAINTS_ADMIN_AND_EMERGENCY_ADMIN,

    /**
     * 被指派处理人员
     */
    EVENT_DEAL_PERSON,

    ;


 }
