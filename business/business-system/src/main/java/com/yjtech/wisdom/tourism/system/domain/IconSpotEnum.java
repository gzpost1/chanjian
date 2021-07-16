package com.yjtech.wisdom.tourism.system.domain;

import lombok.Getter;

/**
 * 图表点位类型
 * @author liuhong
 * @date 2021-07-12 9:10
 */
public enum IconSpotEnum {
    /**
     * 闸机
     */
    TURNSTILE("闸机", "1"),
    /**
     * 停车场
     */
    DEPOT("停车场", "2"),
    /**
     * 视频监控
     */
    VIDEO("视频监控", "3"),
    /**
     * wifi
     */
    WIFI("wifi", "4"),
    /**
     * 报警柱
     */
    ALARM("报警柱", "5"),
    /**
     * 水文
     */
    HYDROLOGICAL("水文", "6"),
    /**
     * 广播
     */
    BROADCAST("广播", "7"),
    /**
     * 厕所
     */
    TOILET("厕所", "8"),
    /**
     * 事件
     */
    EVENT("事件", "9"),
    /**
     * 定位设备
     */
    LOCATION("定位设备", "10");

    @Getter
    private String value;

    @Getter
    private String label;

    IconSpotEnum(String label, String value) {
        this.value = value;
        this.label = label;
    }
}
