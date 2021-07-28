package com.yjtech.wisdom.tourism.common.constant;

/**
 * 消息中心状态常量
 *
 * @author renguangqian
 * @date 2021/7/28 15:10
 */
public class MessageConstants {

    /**
     * 事件类型_旅游投诉
     */
    private final static String EVENT_TYPE_TOUR = "0";

    /**
     * 事件类型_应急事件
     */
    private final static String EVENT_TYPE_EMERGENCY = "1";

    /**
     * 事件状态_待指派
     */
    private final static Integer EVENT_STATUS_APPOINT = 0;

    /**
     * 事件状态_待处理
     */
    private final static Integer EVENT_STATUS_DEAL = 1;

    /**
     * 事件状态_已处理
     */
    private final static Integer EVENT_STATUS_COMPLETE = 2;
}
