package com.yjtech.wisdom.tourism.integration.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 运营数据信息
 *
 * @Author horadirm
 * @Date 2021/7/28 16:39
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OperationDataInfo implements Serializable {

    private static final long serialVersionUID = 5113874705415845824L;

    /**
     * 入驻景点
     */
    private Integer scenicCount;

    /**
     * 入驻商户（店铺数）
     */
    private Integer storeCount;

    /**
     * 商品总量
     */
    private Integer productCount;

}
