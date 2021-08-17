package com.yjtech.wisdom.tourism.marketing.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 场所 查询VO
 *
 * @Date 2020/11/27 18:13
 * @Author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceQueryVO implements Serializable {

    private static final long serialVersionUID = -1160424331678267093L;


    /**
     * 场所类型(0-民宿，1-景点，2-酒店，3-门票，4-美食，5-购物，6-休闲娱乐)
     */
    private Integer placeType;

}
