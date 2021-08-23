package com.yjtech.wisdom.tourism.decisionsupport.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 辅助决策-模拟数据
 *
 * @author renguangqian
 * @date 2021/8/23 16:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DecisionMockDto implements Serializable {

    private static final long serialVersionUID = 8219645083568601414L;

    /**
     * 指标名称
     */
    private String name;

    /**
     * 风险等级-数据字典
     */
    private String value;

}
