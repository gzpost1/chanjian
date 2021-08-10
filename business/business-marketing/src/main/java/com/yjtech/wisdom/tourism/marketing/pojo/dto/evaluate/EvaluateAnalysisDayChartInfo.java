package com.yjtech.wisdom.tourism.marketing.pojo.dto.evaluate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 评论日趋势图表信息
 *
 * @Date 2020/11/25 16:16
 * @Author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluateAnalysisDayChartInfo implements Serializable {

    private static final long serialVersionUID = -2811519141625449526L;

    /**
     * 日期
     */
    private String evaluateTime;

    /**
     * 住宿评论数量
     */
    private Integer restCount;

    /**
     * 景区评论数量
     */
    private Integer scenicAreaCount;

    /**
     * 美食评论数量
     */
    private Integer delicacyCount;

    public EvaluateAnalysisDayChartInfo(String evaluateTime) {
        this.evaluateTime = evaluateTime;
        this.restCount = 0;
        this.scenicAreaCount = 0;
        this.delicacyCount = 0;
    }
}
