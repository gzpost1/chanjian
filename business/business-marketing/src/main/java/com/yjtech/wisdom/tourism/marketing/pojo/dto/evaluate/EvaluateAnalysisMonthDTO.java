package com.yjtech.wisdom.tourism.marketing.pojo.dto.evaluate;

import com.yjtech.wisdom.tourism.marketing.pojo.dto.AnalysisBaseInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 评论月趋势分析
 *
 * @Author horadirm
 * @Date 2020/11/25 12:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluateAnalysisMonthDTO implements Serializable {

    private static final long serialVersionUID = -448593724421423208L;

    /**
     * 去年评论数量
     */
    private Integer last;

    /**
     * 今年评论数量
     */
    private Integer current;

    /**
     * 月趋势图表
     */
    private List<AnalysisBaseInfo> analysisMonthChart;

}
