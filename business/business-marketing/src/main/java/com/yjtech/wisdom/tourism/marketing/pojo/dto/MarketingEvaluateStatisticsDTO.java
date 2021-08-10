package com.yjtech.wisdom.tourism.marketing.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 评价统计 DTO
 *
 * @Author horadirm
 * @Date 2021/8/10 19:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketingEvaluateStatisticsDTO implements Serializable {

    private static final long serialVersionUID = 5405274191112108894L;

    /**
     * 评价总数
     */
    private Integer evaluateTotal;

    /**
     * 满意度
     */
    private BigDecimal satisfaction;

    /**
     * 评分（平均）
     */
    private BigDecimal rate;

}
