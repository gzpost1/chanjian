package com.yjtech.wisdom.tourism.marketing.pojo.dto.evaluate;

import lombok.Data;

import java.io.Serializable;

/**
 * 评论图表信息
 *
 * @Date 2020/11/25 10:34
 * @Author horadirm
 */
@Data
public class EvaluateChartInfo implements Serializable {

    private static final long serialVersionUID = -955440761488050198L;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 评价数量
     */
    private Integer evaluateCount;

}
