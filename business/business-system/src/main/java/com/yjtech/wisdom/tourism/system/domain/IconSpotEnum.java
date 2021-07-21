package com.yjtech.wisdom.tourism.system.domain;

import lombok.Getter;

/**
 * 图表点位类型
 * @author liuhong
 * @date 2021-07-12 9:10
 */
public enum IconSpotEnum {
    /**
     * 景区
     */
    SCENIC("景区", "1"),
    /**
     * 酒店
     */
    HOTEL("酒店", "2"),
    /**
     * 乡村游
     */
    COUNTRY("乡村游", "3"),
    /**
     * 文博场馆
     */
    MUSEUM("文博场馆", "4"),
    /**
     * 视频监控
     */
    VIDEO("视频监控", "5"),
    /**
     * 应急事件
     */
    EVENT("应急事件", "6");


    @Getter
    private String value;

    @Getter
    private String label;

    IconSpotEnum(String label, String value) {
        this.value = value;
        this.label = label;
    }
}
