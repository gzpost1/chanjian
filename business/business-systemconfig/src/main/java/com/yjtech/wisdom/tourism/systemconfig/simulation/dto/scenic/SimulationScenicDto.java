package com.yjtech.wisdom.tourism.systemconfig.simulation.dto.scenic;

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
public class SimulationScenicDto extends SimulationCommonDto {

    /**
     * 小时初始客流量
     */
    @NotNull(message = "小时初始客流量不能为空")
    private BigDecimal initialHourlyQuantity;



    /**
     * 日累计客流加数
     */
    @NotNull(message = "日累计客流加数不能为空")
    private BigDecimal dailyAddend;

    /**
     * 月累计客流量加数
     */
    @NotNull(message = "月累计客流量加数不能为空")
    private BigDecimal monthlyAddend;


    /**
     * 日评价初始数
     */
    @NotNull(message = "日评价初始数不能为空")
    private BigDecimal initialDailyEvaluate;


    /**
     * 月累计评价量加数
     */
    @NotNull(message = "月累计评价加数不能为空")
    private BigDecimal monthlyEvaluateAddend;

    /**
     * 评分初始数
     */
    @NotNull(message = "评分初始数不能为空")
    private BigDecimal initialScore;

    /**
     * 好评占比初始数
     */
    @NotNull(message = "好评占比初始数不能为空")
    private BigDecimal initialPraiseRate;


    /**
     * 评论热词词频
     */
    @Valid
    @NotEmpty(message = "评论热词词频不能为空")
    private List<BaseDto> hotWords;




}
