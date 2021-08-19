package com.yjtech.wisdom.tourism.decisionsupport.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 比列
 *
 * @author renguangqian
 * @date 2021/8/18 20:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ScaleDto implements Serializable {

    private static final long serialVersionUID = 4741024177998933084L;

    /**
     * 名称
     */
    private String name;

    /**
     * 环比
     */
    private String hb;

    /**
     * 同比
     */
    private String tb;

    /**
     * 上月满意度
     */
    private String lastMonthSatisfaction;

    /**
     * 上上月满意度
     */
    private String lastLastMonthSatisfaction;

    /**
     * 去年同月满意度
     */
    private String lastYearLastMonthSatisfaction;
}
