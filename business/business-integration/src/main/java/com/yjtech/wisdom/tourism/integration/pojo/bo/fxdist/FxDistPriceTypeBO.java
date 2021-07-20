package com.yjtech.wisdom.tourism.integration.pojo.bo.fxdist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 珊瑚礁 销售额 BO
 *
 * @Author horadirm
 * @Date 2021/5/24 19:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FxDistPriceTypeBO implements Serializable {

    private static final long serialVersionUID = -2875028951812418256L;

    /**
     * 类型
     */
    private Byte type;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 销售额
     */
    private BigDecimal currentPrice;

}
