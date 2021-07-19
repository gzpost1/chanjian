package com.yjtech.wisdom.tourism.integration.pojo.bo.smarttravel;

import lombok.Data;

import java.io.Serializable;

/**
 * 珊瑚礁 预约统计 BO
 *
 * @Author horadirm
 * @Date 2021/5/24 19:09
 */
@Data
public class SmartTravelReservationStatisticsBO implements Serializable {
    private static final long serialVersionUID = -669027815980321808L;

    /**
     * 预约人数 总数
     */
    private Integer reservationNum;

    /**
     * 未到时 预约人数
     */
    private Integer scanNum;
}
