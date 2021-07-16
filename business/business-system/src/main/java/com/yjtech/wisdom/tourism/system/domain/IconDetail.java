package com.yjtech.wisdom.tourism.system.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 李波
 * @description: 图标的详情
 * @date 2021/7/811:18
 */
@Data
public class IconDetail implements Serializable {
    /**
     * 图标位置
     */
    private String position;

    /**
     * 状态文本
     */
    private String statusStr;

    /**
     * 状态
     */
    private String status;

    /**
     * url
     */
    private String url;
}
