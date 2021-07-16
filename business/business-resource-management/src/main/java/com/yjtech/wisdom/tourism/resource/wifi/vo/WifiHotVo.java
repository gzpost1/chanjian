package com.yjtech.wisdom.tourism.resource.wifi.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WiFi热门点位
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class WifiHotVo {

    /**wifi名称*/
    private String name;

    /**wifi 当前连接数*/
    private Integer currentConnect;

    /**wifi 最大连接数*/
    private Integer connectTotal;
}