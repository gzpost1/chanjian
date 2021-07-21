package com.yjtech.wisdom.tourism.integration.pojo.bo.fxdist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 珊瑚礁 景区销售列表 BO
 *
 * @Author horadirm
 * @Date 2021/5/31 10:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FxDistSaleRankListBO implements Serializable {

    private static final long serialVersionUID = -2903414806965030234L;

    /**
     * 数据id
     */
    private Long dataId;

    /**
     * 数据名称
     */
    private String dataName;

    /**
     * 销售额
     */
    private BigDecimal sale;

}
