package com.yjtech.wisdom.tourism.resource.ticket.vo;

import com.yjtech.wisdom.tourism.common.bean.BaseSaleTrendVO;
import lombok.Data;

/**
 * @author xulei
 * @create 2021-07-03 14:41
 */
@Data
public class SaleTrendVO extends BaseSaleTrendVO {
    /**
     * 售票数量
     */
    private Integer saleQuantity;

    /**
     * 检票数量
     */
    private Integer visitQuantity;
}
