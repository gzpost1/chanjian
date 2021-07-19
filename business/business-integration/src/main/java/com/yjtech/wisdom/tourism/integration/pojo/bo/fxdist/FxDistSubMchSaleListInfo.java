package com.yjtech.wisdom.tourism.integration.pojo.bo.fxdist;

import lombok.Data;

import java.io.Serializable;

/**
 * 珊瑚礁 店铺销售额列表 BO
 *
 * @Author horadirm
 * @Date 2021/5/27 15:29
 */
@Data
public class FxDistSubMchSaleListInfo implements Serializable {

    private static final long serialVersionUID = -4433126507637615005L;

    /**
     * 店铺id
     */
    private Long subMchId;

    /**
     * 销售额
     */
    private Integer sale;

}
