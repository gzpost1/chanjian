package com.yjtech.wisdom.tourism.marketing.pojo.vo;

import com.yjtech.wisdom.tourism.mybatis.entity.TimeBaseQuery;
import lombok.Data;

/**
 * 酒店房型 大屏查询VO
 *
 * @Date 2021/8/11 11:17
 * @Author horadirm
 */
@Data
public class RoomScreenQueryVO extends TimeBaseQuery {

    private static final long serialVersionUID = 4798984563364843783L;

    /**
     * 酒店id
     */
    private String hotelId;

}
