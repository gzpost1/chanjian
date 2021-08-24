package com.yjtech.wisdom.tourism.systemconfig.simulation.dto.districttour;

import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.SimulationCommonDto;
import lombok.Data;

import java.util.List;

/**
 * 游客结构-模拟数据
 *
 * @author renguangqian
 * @date 2021/8/23 16:58
 */
@Data
public class DistrictMockRuleDTO extends SimulationCommonDto {

    /**
     * 随机数-开始
     */
    private Integer randomStart = -20;

    /**
     * 随机数-结束
     */
    private Integer randomEnd = 20;

    /**
     * 日客流量 固定值
     */
    private Integer dayPassengerFlowValue = 1000;

    /**
     * 月客流量 固定值
     */
    private Integer monthPassengerFlowValue = 0;

    /**
     * 省外来源分布
     */
    private List<OriginDistributedProvinceOutsideDTO> provinceOutsideDistributed;

    /**
     * 省内来源分布
     */
    private List<OriginDistributedProvinceInsideDTO> provinceInsideDistributed;
}
