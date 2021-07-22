package com.yjtech.wisdom.tourism.common.enums;

import lombok.Getter;

/**
 * 旅游投诉类型
 *
 * @Author horadirm
 * @Date 2021/7/21 15:33
 */
public enum TravelComplaintTypeEnum {

    /**
     * 其他
     */
    TRAVEL_COMPLAINT_TYPE_ELSE((byte)0, "其他"),

    /**
     * 景区
     */
    TRAVEL_COMPLAINT_TYPE_SCENIC((byte)1, "景区"),

    /**
     * 酒店
     */
    TRAVEL_COMPLAINT_TYPE_HOTEL((byte)2, "酒店"),

    ;

    @Getter
    private Byte value;
    @Getter
    private String describe;

    TravelComplaintTypeEnum(Byte value, String describe) {
        this.value = value;
        this.describe = describe;
    }

    /**
     * 根据值获取枚举
     *
     * @param value
     * @return
     */
    public static TravelComplaintTypeEnum getItemByValue(Byte value) {
        for (TravelComplaintTypeEnum item : TravelComplaintTypeEnum.values()) {
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
        for (TravelComplaintTypeEnum item : TravelComplaintTypeEnum.values()) {
            if (item.getValue().equals(value)) {
                return item.getDescribe();
            }
        }
        return "";
    }

}
