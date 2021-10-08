package com.yjtech.wisdom.tourism.common.enums;

import lombok.Getter;

/**
 * 默认时间筛选类型
 *
 * @date 2021/9/28 9:12
 * @author horadirm
 */
public enum PlatformDefaultTimeTypeEnum {

    /**
     * 七天
     */
    PLATFORM_DEFAULT_TIME_TYPE_SEVEN_DAY((byte)1, "七天"),

    /**
     * 三十天
     */
    PLATFORM_DEFAULT_TIME_TYPE_THIRTY_DAY((byte)2, "三十天"),

    /**
     * 九十天
     */
    PLATFORM_DEFAULT_TIME_TYPE_NINETY_DAY((byte)3, "九十天"),

    /**
     * 其他
     */
    PLATFORM_DEFAULT_TIME_TYPE_ELSE((byte)4, "其他"),

    ;

    @Getter
    private Byte value;
    @Getter
    private String describe;

    PlatformDefaultTimeTypeEnum(Byte value, String describe) {
        this.value = value;
        this.describe = describe;
    }

    /**
     * 根据值获取枚举
     *
     * @param value
     * @return
     */
    public static PlatformDefaultTimeTypeEnum getItemByValue(Byte value) {
        for (PlatformDefaultTimeTypeEnum item : PlatformDefaultTimeTypeEnum.values()) {
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
        for (PlatformDefaultTimeTypeEnum item : PlatformDefaultTimeTypeEnum.values()) {
            if (item.getValue().equals(value)) {
                return item.getDescribe();
            }
        }
        return null;
    }

}
