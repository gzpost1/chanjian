package com.yjtech.wisdom.tourism.decisionsupport.business.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 分析决策风险
 *
 * @author renguangqian
 * @date 2021/8/11 14:52
 */
@Data
public class AnalyzeDecisionWarnVo implements Serializable {

    private static final long serialVersionUID = -331407363531709265L;

    /**
     * is_simulation 是否有模拟数据 1有 0无
     */
    private Byte isSimulation = 0;
}
