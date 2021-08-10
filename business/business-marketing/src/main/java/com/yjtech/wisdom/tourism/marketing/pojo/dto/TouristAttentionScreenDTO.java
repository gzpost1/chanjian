package com.yjtech.wisdom.tourism.marketing.pojo.dto;

import com.yjtech.wisdom.tourism.marketing.pojo.dto.evaluate.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 游客关注 大屏信息
 *
 * @Date 2020/11/25 11:46
 * @Author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TouristAttentionScreenDTO implements Serializable {

    private static final long serialVersionUID = -5893515035460641354L;

    /**
     * 评论排名
     */
    private List<EvaluateRankingInfo> ranking;

    /**
     * 游客印象
     */
    private List<EvaluateHotInfo> impression;

    /**
     * 行业评论分布
     */
    private List<EvaluateDistributionInfo> businessDistribution;

    /**
     * 评论来源分布
     */
    private List<EvaluateDistributionInfo> sourcesDistribution;

    /**
     * 月趋势分析
     */
    private EvaluateAnalysisMonthDTO month;

    /**
     * 日趋势分析
     */
    private EvaluateAnalysisDayDTO day;

}
