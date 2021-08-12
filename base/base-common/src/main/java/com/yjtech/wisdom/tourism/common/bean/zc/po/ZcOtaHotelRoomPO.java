package com.yjtech.wisdom.tourism.common.bean.zc.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 中测酒店房型 PO
 *
 * @Author horadirm
 * @Date 2020/11/26 9:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZcOtaHotelRoomPO implements Serializable {

    private static final long serialVersionUID = 6829528545837266485L;

    /**
     * 第三方主键id
     */
    private String id;

    /**
     * 酒店id
     */
    private String hotelId;

    /**
     * 房型名称
     */
    private String roomName;

    /**
     * 房型
     */
    private Byte roomType;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 价格日期
     */
    private LocalDate priceTime;

    /**
     * 床型
     */
    private String bedType;

    /**
     * 面积
     */
    private String area;

}
