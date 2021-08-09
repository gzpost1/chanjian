package com.yjtech.wisdom.tourism.common.bean;

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
    private String time;

    /**
     * 数据量
     */
    private Integer count;

    /**
     * 同比
     */
    private BigDecimal same;

    /**
     * 环比
     */
    private BigDecimal sequential;


    public AnalysisMonthChartInfo(String time) {
        this.time = time;
        this.count = 0;
    }
}
