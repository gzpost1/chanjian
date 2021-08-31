package com.yjtech.wisdom.tourism.common.constant;

/**
 * 消息中心状态常量
 *
 * @author renguangqian
 * @date 2021/7/28 15:10
 */
public class MessageConstants {

    /**
     * 查询类型_全部
     */
    public final static Integer QUERY_ALL = 0;
    /**
     * null
     */
    public final static String NULL = "null";

    /**
     * 查询类型_待指派、处理
     */
    public final static Integer QUERY_DEAL = 1;


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

    /**
     * 消息记录缓存key
     */
    public final static String MESSAGE_RECORD_NUM = "MESSAGE_RECORD_NUM_";

     /**
     * 消息列表查询类型_全部
     */
    public final static int MESSAGE_LIST_ALL = 0;

     /**
     * 消息列表查询类型_待指派/待处理信息
     */
    public final static int MESSAGE_LIST_PENDING = 1;



}
