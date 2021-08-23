package com.yjtech.wisdom.tourism.marketing.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 酒店房型价格 大屏DTO
 *
 * @Author horadirm
 * @Date 2021/8/11 10:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypePriceScreenDTO implements Serializable {

    private static final long serialVersionUID = 7639593304855404351L;

    /**
     * 历史最高价
     */
    private BigDecimal highestPrice;

    /**
     * 历史最低价
     */
    private BigDecimal lowestPrice;

    /**
     * 平均价格
     */
    private BigDecimal averagePrice;

}
