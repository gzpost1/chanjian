package com.yjtech.wisdom.tourism.decisionsupport.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 综合概况-报警数据
 *
 * @author renguangqian
 * @date 2021/8/7 12:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ComprehensiveAlarmDataDto implements Serializable {

    private static final long serialVersionUID = -1084568204749583011L;

    /**
     * 低风险报警数量
     */
    private Integer lowRiskNumber;

    /**
     * 中风险报警数量
     */
    private Integer mediumRiskNumber;

    /**
     * 高风险报警数量
     */
    private Integer highRiskNumber;

    /**
     * 低风险报警名称
     */
    private String lowRiskName;

    /**
     * 中风险报警名称
     */
    private String mediumRiskName;

    /**
     * 高风险报警名称
     */
    private String highRiskName;

    /**
     * 报警总数量
     */
    private Integer totalRiskNumber;

}
