package com.yjtech.wisdom.tourism.common.constant;

/**
 * 模板常量
 *
 * @date 2021/8/19 18:02
 * @author horadirm
 */
public class TemplateConstants {

    /**
     *  消息模板-后台-旅游投诉-新增
     *  收到旅游投诉，请指定专人进行处理！｛投诉类型｝｛投诉对象｝｛投诉日期｝｛事发地点｝。
     */
    public static final String TEMPLATE_PLATFORM_TRAVEL_COMPLAINT_INSERT = "收到旅游投诉，请指定专人进行处理！{0}{1}{2}{3}。";

    /**
     *  消息模板-后台-旅游投诉-指派（待处理）
     *  收到旅游投诉，请尽快处理！｛投诉类型｝｛投诉对象｝｛投诉日期｝｛事发地点｝。
     */
    public static final String TEMPLATE_PLATFORM_TRAVEL_COMPLAINT_ASSIGN = "收到旅游投诉，请尽快处理！{0}{1}{2}{3}。";

    /**
     *  消息模板-APP-旅游投诉-指派（待处理）
     *  收到旅游投诉，请尽快处理！投诉对象：｛投诉对象名称｝。
     */
    public static final String TEMPLATE_APP_TRAVEL_COMPLAINT_ASSIGN = "收到旅游投诉，请尽快处理！投诉对象：{0}";





}