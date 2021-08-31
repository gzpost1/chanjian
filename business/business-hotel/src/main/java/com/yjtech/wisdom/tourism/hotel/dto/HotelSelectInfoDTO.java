package com.yjtech.wisdom.tourism.hotel.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 酒店下拉选 DTO
 *
 * @date 2021/8/25 10:12
 * @author horadirm
 */
@Data
public class HotelSelectInfoDTO implements Serializable {

    private static final long serialVersionUID = 4348255943780184147L;

    /**
     * 酒店id
     */
    private Long id;

    /**
     * 酒店名称
     */
    private String name;

}
