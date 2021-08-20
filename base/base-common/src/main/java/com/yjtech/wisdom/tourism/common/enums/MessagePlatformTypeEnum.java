package com.yjtech.wisdom.tourism.common.enums;

import lombok.Getter;

/**
 * 旅游投诉状态
 *
 * @Author horadirm
 * @Date 2021/7/21 15:33
 */
public enum MessagePlatformTypeEnum {

    /**
     * 后台
     */
    MESSAGE_PLATFORM_TYPE_BACK((byte)0, "后台"),

    /**
     * app
     */
    MESSAGE_PLATFORM_TYPE_APP((byte)1, "app"),

    /**
     * 短信
     */
    MESSAGE_PLATFORM_TYPE_SHORT_MESSAGE((byte)2, "短信"),

    ;

    @Getter
    private Byte value;
    @Getter
    private String describe;

    MessagePlatformTypeEnum(Byte value, String describe) {
        this.value = value;
        this.describe = describe;
    }

    /**
     * 根据值获取枚举
     *
     * @param value
     * @return
     */
    public static MessagePlatformTypeEnum getItemByValue(Byte value) {
        for (MessagePlatformTypeEnum item : MessagePlatformTypeEnum.values()) {
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
        for (MessagePlatformTypeEnum item : MessagePlatformTypeEnum.values()) {
            if (item.getValue().equals(value)) {
                return item.getDescribe();
            }
        }
        return "";
    }

}
