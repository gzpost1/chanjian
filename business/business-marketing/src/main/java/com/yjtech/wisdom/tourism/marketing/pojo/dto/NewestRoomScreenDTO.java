package com.yjtech.wisdom.tourism.marketing.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 最新房型信息 大屏DTO
 *
 * @Author horadirm
 * @Date 2021/8/11 13:42
 */
@Data
public class NewestRoomScreenDTO implements Serializable {

    private static final long serialVersionUID = -6288072223898240604L;

    /**
     * 房型名称
     */
    private String roomName;

    /**
     * 房型价格
     */
    private BigDecimal roomPrice;

    /**
     * 房型图片
     */
    private String roomImg;


}
