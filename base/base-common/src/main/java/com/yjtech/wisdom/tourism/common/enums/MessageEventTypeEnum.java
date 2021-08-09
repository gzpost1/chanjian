package com.yjtech.wisdom.tourism.common.enums;

import lombok.Getter;

/**
 * 消息事件类型
 *
 * @Author horadirm
 * @Date 2021/7/29 17:13
 */
public enum MessageEventTypeEnum {

    /**
     * 旅游投诉
     */
    MESSAGE_EVENT_TYPE_TRAVEL_COMPLAINT((byte)0, "旅游投诉"),

    /**
     * 应急事件
     */
    MESSAGE_EVENT_TYPE_EMERGENCY_EVENT((byte)1, "应急事件"),


    ;

    @Getter
    private Byte value;
    @Getter
    private String describe;

    MessageEventTypeEnum(Byte value, String describe) {
        this.value = value;
        this.describe = describe;
    }

    /**
     * 根据值获取枚举
     *
     * @param value
     * @return
     */
    public static MessageEventTypeEnum getItemByValue(Byte value) {
        for (MessageEventTypeEnum item : MessageEventTypeEnum.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 根据值获取描述
     *
     * @param value
     * @return
     */
    public static String getDescByValue(Byte value) {
        for (MessageEventTypeEnum item : MessageEventTypeEnum.values()) {
            if (item.getValue().equals(value)) {
                return item.getDescribe();
            }
        }
        return "";
    }

}
