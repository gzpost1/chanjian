package com.yjtech.wisdom.tourism.hotel.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yjtech.wisdom.tourism.hotel.dto.DateNumberDTO;
import com.yjtech.wisdom.tourism.hotel.dto.RoomTypeInflowInfoDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author Mujun~
 * @Date 2020-08-12 9:26
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelInfoVo<T> {

    private T data;
    private List<DateNumberDTO> dayTrend;

    /**
     * 客房数
     */
    private int roomNum;
    /**
     * 入住房数
     */
    private int uesedRoomNum;
    /**
     * 入住人数
     */
    private int inflowNum;

    /**
     * 总客房数
     */
    List<StaticNumVo> roomNumList;
    /**
     * 客房数
     */
    List<StaticNumVo> useRoomNumList;

    /**
     * 房型入住信息
     */
    List<RoomTypeInflowInfoDTO> roomTypeInflowInfos;
}
