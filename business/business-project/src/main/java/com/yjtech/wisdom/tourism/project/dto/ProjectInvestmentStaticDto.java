package com.yjtech.wisdom.tourism.project.dto;

import lombok.Data;

import java.util.List;

/**
 * @author renguangqian
 * @date 2022年08月03日 14:32
 */
@Data
public class ProjectInvestmentStaticDto {

    /**
     * 总投资额
     */
    private Long totalMoney;

    /**
     * 比较金额
     */
    private Long compareMoney;

    /**
     * 项目投资金额统计
     */
    private List<ProjectInvestmentDto> list;
}
