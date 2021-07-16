package com.yjtech.wisdom.tourism.resource.depot.entity.vo;

import com.yjtech.wisdom.tourism.common.bean.BaseSaleTrendVO;
import lombok.Data;

/**
 * @author zc
 * @create 2021-07-03 14:41
 */
@Data
public class DepotTrendVo extends BaseSaleTrendVO {

    /**
     * 进入数量
     */
    private Integer enterQuantity;

    /**
     * 出数量
     */
    private Integer exitQuantity;
}
