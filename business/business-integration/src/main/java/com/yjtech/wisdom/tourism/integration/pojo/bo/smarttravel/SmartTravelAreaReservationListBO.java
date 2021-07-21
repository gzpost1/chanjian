package com.yjtech.wisdom.tourism.integration.pojo.bo.smarttravel;

import lombok.Data;

import java.io.Serializable;

/**
 * 景区中心 区域预约 BO
 *
 * @Author horadirm
 * @Date 2021/5/26 13:58
 */
@Data
public class SmartTravelAreaReservationListBO implements Serializable {

    private static final long serialVersionUID = -6909568305932857864L;

    /**
     * x轴信息（区域信息）
     */
    private String xName;

    /**
     * y轴信息（预约数量）
     */
    private Integer yInfo;

}
