package com.yjtech.wisdom.tourism.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 本年客流趋势
 *
 * @author renguangqian
 * @date 2021/7/22 19:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class YearPassengerFlowDto implements Serializable {

    private static final long serialVersionUID = -7626358300178284878L;

    /**
     * 当前月的人数
     */
    private Integer curNumber;
    /**
     * 当前月时间
     */
    private String curDate;
    /**
     * 同比人数
     */
    private Integer tbNumber;

    /**
     * 同比比例
     */
    //private String hbScale;

    /**
     * 同比时间
     */
    private String tbDate;

    /**
     * 环比时间
     */
    private String hbDate;
    /**
     * 环比人数
     */
    private Integer hbNumber;

    /**
     * 环比比例
     */
    //private String hbScale;

    /**
     * 当前年
     */
    private String curYear;
    /**
     * 同比年
     */
    private String lastYear;
    /**
     * 月份
     */
    private String curMonth;

    /**
     * 同比
     */
    private Double yearOnYear;

    /**
     * 环比
     */
    private Double monthOnMonth;


}
