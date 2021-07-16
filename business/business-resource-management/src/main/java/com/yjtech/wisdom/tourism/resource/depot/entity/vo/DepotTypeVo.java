package com.yjtech.wisdom.tourism.resource.depot.entity.vo;

import com.yjtech.wisdom.tourism.common.bean.BaseSaleTrendVO;
import lombok.Data;

/**
 * @author zc
 * @create 2021-07-03 14:41
 */
@Data
public class DepotTypeVo extends BaseSaleTrendVO {

    /**数量*/
    private Integer quantity;
    /**数量*/
    private String name;

}
