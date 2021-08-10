package com.yjtech.wisdom.tourism.decisionsupport.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 决策预警大屏
 *
 * @author renguangqian
 * @date 2021/7/28 21:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DecisionWarnWrapperDto implements Serializable {

    private static final long serialVersionUID = -367197400337838946L;

    /**
     * 预警总数
     */
    private Integer warnTotal;

    /**
     * 低风险项数值
     */
    private Integer lowRiskNum;

    /**
     * 中风险项数值
     */
    private Integer mediumRiskNum;

    /**
     * 高风险项数值
     */
    private Integer highRiskNum;

    /**
     * 分析年月 yyyy-MM
     */
    private String analyzeDate;

    /**
     * 上次分析时间
     */
    private String lastAnalyzeDate;

    /**
     * 话术
     */
    private String conclusionText;

    /**
     * 具体预警列表
     */
    private List<DecisionWarnItemDto> list;

}
