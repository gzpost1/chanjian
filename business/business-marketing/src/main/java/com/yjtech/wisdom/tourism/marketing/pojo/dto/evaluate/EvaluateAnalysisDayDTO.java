package com.yjtech.wisdom.tourism.marketing.pojo.dto.evaluate;

import com.yjtech.wisdom.tourism.marketing.pojo.dto.AnalysisBaseInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * 评论日趋势分析
 *
 * @Date 2020/11/25 11:53
 * @Author horadirm
 */
@Data
public class EvaluateAnalysisDayDTO implements Serializable {

    private static final long serialVersionUID = -448593724421423208L;

    /**
     * 景区评论数量
     */
    private Integer scenicAreaEvaluate;

    /**
     * 住宿评论数量
     */
    private Integer restEvaluate;

    /**
     * 美食评论数量
     */
    private Integer delicacyEvaluate;

    /**
     * 日趋势图表
     */
    private AnalysisBaseInfo analysisDayChart;

}
