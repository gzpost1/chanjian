package com.yjtech.wisdom.tourism.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 排行
 *
 * @author renguangqian
 * @date 2021/8/18 10:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RankingDto implements Serializable {

    private static final long serialVersionUID = 7791583748657559309L;

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 数量
     */
    private String value;

    /**
     * 比例
     */
    private String scale;

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
