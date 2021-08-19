package com.yjtech.wisdom.tourism.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 游客结构 模拟数据
 *
 * @author renguangqian
 * @date 2021/8/13 9:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DistrictMockRuleDto implements Serializable {

    private static final long serialVersionUID = -2703169786515766941L;

    /**
     * 随机数-开始
     */
    private int randomStart;

    /**
     * 随机数-结束
     */
    private int randomEnd;

    /**
     * 日客流量 固定值
     */
    private int dayPassengerFlowValue;

    /**
     * 月客流量 固定值
     */
    private int monthPassengerFlowValue;

    /**
     * 省外来源分布
     */
    private List<OriginDistributedProvinceOutsideDto> provinceOutsideDistributed;
}
