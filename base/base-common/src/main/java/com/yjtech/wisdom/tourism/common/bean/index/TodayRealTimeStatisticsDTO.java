package com.yjtech.wisdom.tourism.common.bean.index;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 今日实时统计
 *
 * @date 2021/8/19 10:48
 * @author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodayRealTimeStatisticsDTO implements Serializable {

    private static final long serialVersionUID = 797256192077751944L;

    /**
     * 今日接待人次
     */
    private Long todayReception;

    /**
     * 景区承载量
     */
    private BigDecimal scenic;

    /**
     * 一码游访问次数
     */
    private BigDecimal oneTravelVisit;

}
