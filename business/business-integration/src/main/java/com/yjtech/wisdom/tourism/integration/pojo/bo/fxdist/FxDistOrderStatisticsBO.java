package com.yjtech.wisdom.tourism.integration.pojo.bo.fxdist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 珊瑚礁 订单统计 BO
 *
 * @Author horadirm
 * @Date 2021/7/28 15:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FxDistOrderStatisticsBO implements Serializable {

    private static final long serialVersionUID = 7682571328124528789L;

    /**
     * 订单总量
     */
    private Long orderCount;

    /**
     * 订单总额
     */
    private BigDecimal orderSum;

}
