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
    public final static Byte EVENT_TYPE_TOUR = 0;

    /**
     * 事件类型_应急事件
     */
    public final static Byte EVENT_TYPE_EMERGENCY = 1;

    /**
     * 事件状态_待指派
     */
    public final static Integer EVENT_STATUS_APPOINT = 0;

    /**
     * 事件状态_待处理
     */
    public final static Integer EVENT_STATUS_DEAL = 1;

    /**
     * 事件状态_已处理
     */
    public final static Integer EVENT_STATUS_COMPLETE = 2;


    /**
     * 应急事件消息 title
     */
    public final static String event_message = "突发应急事件，请指定专人进行处理！";

    /**
     * 应急事件消息 content
     */
    public final static String event_content = "事件名称：%s";




}
