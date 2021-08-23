package com.yjtech.wisdom.tourism.vo;

import com.yjtech.wisdom.tourism.dto.OriginDistributedProvinceOutsideDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 游客结构 模拟数据创建规则
 *
 * @author renguangqian
 * @date 2021/8/12 10:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DistrictMockRuleVo implements Serializable {

    private static final long serialVersionUID = -2929053156758402207L;

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
    private int dayPassengerFlowValue = 1000;

    /**
     * 月客流量 固定值
     */
    private int monthPassengerFlowValue = 0;

    /**
     * 省外来源分布
     */
    private List<OriginDistributedProvinceOutsideDto> provinceOutsideDistributed;

}
