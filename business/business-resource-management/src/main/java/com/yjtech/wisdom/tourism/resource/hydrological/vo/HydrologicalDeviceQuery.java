package com.yjtech.wisdom.tourism.resource.hydrological.vo;

import com.yjtech.wisdom.tourism.common.core.domain.PageQuery;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 *
 * @author wuyongchong
 * @since 2020-09-16
 */
@Getter
@Setter
public class HydrologicalDeviceQuery extends PageQuery {
    /**
    * 监测点名称
    */
    private String name;

    /**
    * 状态(0:禁用,1:启用)
    */
    private String status;



}