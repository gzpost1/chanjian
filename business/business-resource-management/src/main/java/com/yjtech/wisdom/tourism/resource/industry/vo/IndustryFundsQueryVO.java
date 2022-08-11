package com.yjtech.wisdom.tourism.resource.industry.vo;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * 产业资金管理(TbIndustryFunds) 查询VO
 *
 * @author horadirm
 * @since 2022-08-06 10:13:36
 */
@Data
public class IndustryFundsQueryVO extends PageQuery {
    private static final long serialVersionUID = -9025700357547748127L;

    /**
     * 状态 0禁用 1启用
     */
    @Range(min = 0, max = 1, message = "状态不合法")
    private Byte status;

    /**
     * 企业名称
     */
    private String companyName;

}