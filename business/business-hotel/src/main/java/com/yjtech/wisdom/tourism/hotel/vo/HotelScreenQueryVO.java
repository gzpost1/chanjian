package com.yjtech.wisdom.tourism.hotel.vo;

import com.yjtech.wisdom.tourism.mybatis.entity.AreaBaseVO;
import lombok.Data;

/**
 * 酒店大屏 查询 VO
 *
 * @Author horadirm
 * @Date 2021/8/6 15:17
 */
@Data
public class HotelScreenQueryVO extends AreaBaseVO {

    private static final long serialVersionUID = 6693325384661177491L;

    /**
     * 酒店名称
     */
    private String name;

    /**
     * 酒店状态（0-停用 1-正常）
     * 默认 正常
     */
    private Byte status;

}
