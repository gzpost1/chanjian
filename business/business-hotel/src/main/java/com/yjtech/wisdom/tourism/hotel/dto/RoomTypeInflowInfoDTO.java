package com.yjtech.wisdom.tourism.hotel.dto;

import lombok.Data;

/**
 * @author liuhong
 */
@Data
public class RoomTypeInflowInfoDTO {
    /**
     * 房型名称
     */
    private String name;

    /**
     * 总数量
     */
    private Integer number;

    /**
     * 入住数
     */
    private Integer inflowNumber;
}
