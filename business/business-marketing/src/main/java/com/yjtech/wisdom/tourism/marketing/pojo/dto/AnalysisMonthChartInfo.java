package com.yjtech.wisdom.tourism.marketing.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 月趋势图表信息
 *
 * @Author horadirm
 * @Date 2020/11/27 13:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisMonthChartInfo implements Serializable {

    private static final long serialVersionUID = -4624651894094523650L;

    /**
     * 日期
     */
    private String xTime;

    /**
     * 数据量
     */
    private Integer yCount;

    /**
     * 同比
     */
    private BigDecimal same;

    /**
     * 环比
     */
    private BigDecimal sequential;


    public AnalysisMonthChartInfo(String xTime) {
        this.xTime = xTime;
        this.yCount = 0;
//        this.same = BigDecimal.ZERO;
//        this.sequential = BigDecimal.ZERO;
    }
}
