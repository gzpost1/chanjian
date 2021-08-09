package com.yjtech.wisdom.tourism.hotel.vo;

import com.yjtech.wisdom.tourism.mybatis.entity.TimeBaseQuery;
import lombok.Data;

/**
 * 酒店大屏 查询 VO
 *
 * @Author horadirm
 * @Date 2021/8/6 15:17
 */
@Data
public class HotelScreenQueryVO extends TimeBaseQuery {

    private static final long serialVersionUID = 6693325384661177491L;

    /**
     * 酒店名称
     */
    private String name;

}
