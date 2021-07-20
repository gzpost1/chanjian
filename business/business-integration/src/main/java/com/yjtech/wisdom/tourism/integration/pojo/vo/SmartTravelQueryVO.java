package com.yjtech.wisdom.tourism.integration.pojo.vo;

import com.yjtech.wisdom.tourism.common.bean.TimeBaseQuery;
import lombok.Data;

/**
 * 景区中心 查询VO
 *
 * @Author horadirm
 * @Date 2021/5/24 19:50
 */
@Data
public class SmartTravelQueryVO extends TimeBaseQuery {

    private static final long serialVersionUID = -4051036373536659394L;

    /**
     * 景区等级
     */
    private String scenicLevel;

    /**
     * 景区状态
     */
    private Byte status;

}