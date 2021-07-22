package com.yjtech.wisdom.tourism.common.enums;

import lombok.Getter;

/**
 * 旅游投诉状态
 *
 * @Author horadirm
 * @Date 2021/7/21 15:33
 */
public enum TravelComplaintStatusEnum {

    /**
     * 待指派
     */
    TRAVEL_COMPLAINT_STATUS_NO_ASSIGN((byte)0, "待指派"),

    /**
     * 待处理
     */
    TRAVEL_COMPLAINT_STATUS_NO_DEAL((byte)1, "待处理"),

    /**
     * 已处理
     */
    TRAVEL_COMPLAINT_STATUS_DEAL_FINISHED((byte)2, "已处理"),

    ;

    @Getter
    private Byte value;
    @Getter
    private String describe;

    TravelComplaintStatusEnum(Byte value, String describe) {
        this.value = value;
        this.describe = describe;
    }

    /**
     * 根据值获取枚举
     *
     * @param value
     * @return
     */
    public static TravelComplaintStatusEnum getItemByValue(Byte value) {
        for (TravelComplaintStatusEnum item : TravelComplaintStatusEnum.values()) {
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
        for (TravelComplaintStatusEnum item : TravelComplaintStatusEnum.values()) {
            if (item.getValue().equals(value)) {
                return item.getDescribe();
            }
        }
        return "";
    }

}
