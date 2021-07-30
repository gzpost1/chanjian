package com.yjtech.wisdom.tourism.common.enums;

/**
 * 消息中心-话术配置项
 */
public enum MessageConfigItemEnum {

    /**
     * 统计年月
     */
    YEAR_MONTH_STATISTICAL("统计年月", "${YEAR_MONTH_STATISTICAL}"),

    /**
     * 平台简称
     */
    PLATFORM_SIMPLE_NAME("平台简称", "${PLATFORM_SIMPLE_NAME}"),

    /**
     * 省外游客数量
     */
    PROVINCE_OUTSIDE_TOUR_NUM("省外游客数量", "${PROVINCE_OUTSIDE_TOUR_NUM}"),

    /**
     * 环比（较上月）
     */
    PROVINCE_OUTSIDE_TOUR_HB("省外游客数量", "${PROVINCE_OUTSIDE_TOUR_HB}"),

    /**
     * 同比（较去年同月）
     */
    PROVINCE_OUTSIDE_TOUR_TB("同比（较去年同月）", "${PROVINCE_OUTSIDE_TOUR_TB}"),

    ;
    /**
     * 配置项名称
     */
    private String name;

    /**
     * 配置项 key
     */
    private String key;

    MessageConfigItemEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }}
