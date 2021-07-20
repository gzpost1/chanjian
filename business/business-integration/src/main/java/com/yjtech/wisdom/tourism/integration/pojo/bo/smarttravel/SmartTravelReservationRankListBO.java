package com.yjtech.wisdom.tourism.integration.pojo.bo.smarttravel;

import lombok.Data;

import java.io.Serializable;

/**
 * 景区中心 景区预约列表 BO
 *
 * @Date 2021/5/26 11:49
 * @Author horadirm
 */
@Data
public class SmartTravelReservationRankListBO implements Serializable {

    private static final long serialVersionUID = 3677216164903747341L;

    /**
     * 数据id
     */
    private Long dataId;

    /**
     * 数据名称
     */
    private String dataName;

    /**
     * 预约数量
     */
    private Integer number;


}
