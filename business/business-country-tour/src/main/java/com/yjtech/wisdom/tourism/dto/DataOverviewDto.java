package com.yjtech.wisdom.tourism.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 数据总览
 *
 * @author renguangqian
 * @date 2021/7/22 14:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DataOverviewDto implements Serializable {

    private static final long serialVersionUID = -4548823635923199766L;

    /**
     * 全部游客人次
     */
    private Long allTouristNum;

    /**
     * 省外游客人次
     */
    private Long provinceOutsideTouristNum;

    /**
     * 省内游客人次
     */
    private Long provinceInsideTouristNum;

    /**
     * 省内游客占比
     */
    private BigDecimal provinceInsideRate;

    /**
     * 省外游客占比
     */
    private BigDecimal provinceOutsideRate;

}
