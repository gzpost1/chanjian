package com.yjtech.wisdom.tourism.integration.pojo.bo.hotel;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 酒店中心 酒店销售列表 BO
 *
 * @Date 2021/5/28 14:06
 * @Author horadirm
 */
@Data
public class HotelSaleRankListBO implements Serializable {

    private static final long serialVersionUID = -1404869080798216370L;

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
