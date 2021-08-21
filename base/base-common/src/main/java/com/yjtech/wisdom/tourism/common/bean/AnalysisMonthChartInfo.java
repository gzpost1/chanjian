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
    private BigDecimal count;

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
        this.count = BigDecimal.ZERO;
    }

    /**
     * 构建
     * @param time
     * @param count
     * @param lastYearCount 去年同月数据量，用于计算同比
     * @param lastMonthCount 同年上月数据量，用于计算环比
     */
    public AnalysisMonthChartInfo build(String time, BigDecimal count, BigDecimal lastYearCount, BigDecimal lastMonthCount) {
        setTime(time);
        setCount(count);
        setSame(null != lastYearCount && BigDecimal.ZERO.compareTo(lastYearCount) < 0 ?
                (count.subtract(lastYearCount)).divide(lastYearCount,3,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(1,BigDecimal.ROUND_HALF_UP) : null);
        setSequential(null != lastMonthCount && BigDecimal.ZERO.compareTo(lastMonthCount) < 0 ?
                (count.subtract(lastMonthCount)).divide(lastMonthCount,3,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(1,BigDecimal.ROUND_HALF_UP) : null);
        return this;
    }

}
