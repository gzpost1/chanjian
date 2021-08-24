package com.yjtech.wisdom.tourism.systemconfig.simulation.dto.event;

import com.yjtech.wisdom.tourism.common.bean.BaseDto;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.SimulationCommonDto;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 事件模拟数据配置
 *
 * @author 徐雷
 * @date 2021-07-15 10:21
 */
@Data
public class SimulationEventDto extends SimulationCommonDto {
    /**
     * 初始事件量
     */
    @NotNull(message = "初始事件量不能为空")
    private BigDecimal initialQuantity;


    /**
     * 七日累计事件量系数
     */
    @NotNull(message = "七日累计事件量系数不能为空")
    private BigDecimal weeklyCoefficient;

    /**
     * 七日累计事件量加数
     */
    @NotNull(message = "七日累计事件量加数不能为空")
    private BigDecimal weeklyAddend;

    /**
     * 月累计事件量加数
     */
    @NotNull(message = "月累计事件量加数不能为空")
    private BigDecimal monthlyAddend;

    /**
     * 年累计事件量加数
     */
    @NotNull(message = "年累计事件量加数不能为空")
    private BigDecimal yearlyAddend;

    /**
     * 事件类型分布
     */
    @Valid
    @NotEmpty(message = "事件类型分布不能为空")
    private List<BaseDto> eventType;

    /**
     * 事件级别分布分布
     */
    @Valid
    @NotEmpty(message = "事件级别分布不能为空")
    private List<BaseDto> eventLevel;

    /**
     * 已处理事件占比
     */
    @NotNull(message = "已处理事件占比不能为空")
    private BigDecimal handledRate;


}
