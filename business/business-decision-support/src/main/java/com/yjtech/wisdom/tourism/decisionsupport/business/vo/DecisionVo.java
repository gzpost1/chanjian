package com.yjtech.wisdom.tourism.decisionsupport.business.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 决策辅助
 *
 * @author renguangqian
 * @date 2021/7/28 13:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DecisionVo implements Serializable {

    private static final long serialVersionUID = 4364505544633189659L;

    /**
     * 指标id
     */
    @NotNull
    private Long targetId;

    /**
     * 指标名称
     */
    @NotBlank
    private String targetName;

    /**
     * 预警配置id
     */
    @NotNull
    private Long configId;

    /**
     * 预警配置名称
     */
    @NotBlank
    private String configName;

    /**
     * 预警配置类型 0：数值 1：文本
     */
    @NotNull
    private Integer configType;

    /** ======================================= 数值 配置类型 ======================================= */

    /**
     * 变化类型 0：上升  1：下降 2：忽略变化
     */
    private Integer changeType;

    /**
     * 低风险预警值
     */
    private Double lowRiskThreshold;

    /**
     * 中风险预警值
     */
    private Double mediumRiskThreshold;

    /**
     * 高风险预警值
     */
    private Double highRiskThreshold;

    /** ======================================= 文本 配置类型 ======================================= */

    /**
     * 风险类型 0：低风险 1：中风险 2：高风险 3：无风险
     */
    private Integer riskType;

    /**
     * 话术
     */
    private String conclusionText;

}
