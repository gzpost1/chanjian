package com.yjtech.wisdom.tourism.integration.pojo.bo.fxdist;

import lombok.Data;

import java.io.Serializable;

/**
 * 珊瑚礁 区域销售列表 BO
 *
 * @Author horadirm
 * @Date 2021/5/27 15:29
 */
@Data
public class FxDistAreaSaleListBO implements Serializable {

    private static final long serialVersionUID = 7153456443808968329L;

    /**
     * x轴信息（区域信息）
     */
    private String xName;

    /**
     * y轴信息（预约数量）
     */
    private Integer yInfo;


}
