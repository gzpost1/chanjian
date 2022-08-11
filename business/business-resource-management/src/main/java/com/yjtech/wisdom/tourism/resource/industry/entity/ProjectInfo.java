package com.yjtech.wisdom.tourism.resource.industry.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * 产业资金管理-项目情况
 *
 * @date 2022/8/6 10:17
 * @author horadirm
 */
@Data
public class ProjectInfo{

    /**
     * 项目名称
     */
    @NotBlank(message = "项目名称不能为空")
    @Size(min = 1, max = 99, message = "项目名称不合法，仅支持99位")
    private String name;

    /**
     * 产业类型 1文化旅游 2旅游产业 3旅游商品 4批发零售 5住宿餐饮 6中高端住宿业 7商业贸易 8会展服务 9健康养生
     */
    @NotNull(message = "产业类型不能为空")
    @Range(min = 1, max = 9, message = "产业类型不合法")
    private Byte type;

    /**
     * 投资金额 单位：亿元
     */
    @NotNull(message = "投资金额不能为空")
    @Range(min = 0, max = 1000000000, message = "投资金额不合法")
    private BigDecimal investmentSum;

    /**
     * 政策金额 单位：亿元
     */
    @Range(min = 0, max = 1000000000, message = "政策金额不合法")
    private BigDecimal policySum;

}
