package com.yjtech.wisdom.tourism.common.enums;

/**
 * OTA数据场所类型
 *
 * @Author horadirm
 * @Date 2020/11/19 20:28
 */
public enum DataSourceTypeEnum {

    /**
     * 酒店
     */
    DATA_SOURCE_TYPE_HOTEL(0, "酒店", 2),
    /**
     * 民宿
     */
    DATA_SOURCE_TYPE_GUESTHOUSE(1, "民宿", -666),
    /**
     * 景点
     */
    DATA_SOURCE_TYPE_SCENIC_SPOT(2, "景点", 1),
    /**
     * 门票
     */
    DATA_SOURCE_TYPE_TICKET(3, "门票", -666),
    /**
     * 美食
     */
    DATA_SOURCE_TYPE_DELICACY(4, "美食", -666),
    /**
     * 购物
     */
    DATA_SOURCE_TYPE_SHOPPING(5, "购物", -666),
    /**
     * 休闲娱乐
     */
    DATA_SOURCE_TYPE_ENTERTAINMENT(6, "休闲娱乐", -666),

    ;

    private Integer value;
    private String describe;
    /**
     * 冗余类型
     * （在保证自有业务不变的情况下，用于处理黑水县第三方数据的非套路返回）
     */
    private Integer redundancyValue;

    DataSourceTypeEnum(Integer value, String describe, Integer redundancyValue) {
        this.value = value;
        this.describe = describe;
        this.redundancyValue = redundancyValue;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescribe() {
        return describe;
    }

    public Integer getRedundancyValue() {
        return redundancyValue;
    }

    /**
     * 根据值获取描述
     *
     * @param value
     * @return
     */
    public static String getDescByValue(Integer value) {
        for (DataSourceTypeEnum item : DataSourceTypeEnum.values()) {
            if (item.getValue().equals(value)) {
                return item.getDescribe();
            }
        }
        return "--";
    }

    /**
     * 根据冗余类型获取类型
     * @param redundancyValue
     * @return
     */
    public static Integer getValueByRedundancyValue(Integer redundancyValue){
        for (DataSourceTypeEnum item : DataSourceTypeEnum.values()) {
            if (item.getRedundancyValue().equals(redundancyValue)) {
                return item.getValue();
            }
        }
        return null;
    }

}
