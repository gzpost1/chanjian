package com.yjtech.wisdom.tourism.project.vo;

import lombok.Data;

@Data
public class ProjectAmountVo {

    /**
     * 名称
     */
    private String name;

    /**
     * 投资金额
     */
    private String investmentTotal;

    /**
     * 引资金额
     */
    private String fundingAmount;
}
