package com.yjtech.wisdom.tourism.marketing.pojo.dto.evaluate;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 评论行业分布信息
 *
 * @Date 2020/11/25 9:41
 * @Author horadirm
 */
@Data
public class EvaluateDistributionInfo implements Serializable {

    private static final long serialVersionUID = -1640433560748804281L;

    /**
     * 分布名称
     */
    private String distributionName;

    /**
     * 分布数量
     */
    private Integer distributionCount;

    /**
     * 分布占比
     */
    private BigDecimal proportion;

}
