package com.yjtech.wisdom.tourism.common.bean.index;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 酒店民宿
 *
 * @date 2021/8/19 10:57
 * @author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelStatisticsDTO implements Serializable {

    private static final long serialVersionUID = -6809052042307609917L;

    /**
     * 收获评价（条）
     */
    private Integer evaluate;

    /**
     * 满意度
     */
    private BigDecimal satisfaction;

    /**
     * 平均价格（元/晚）
     */
    private BigDecimal averagePrice;

}
