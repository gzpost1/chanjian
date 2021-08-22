package com.yjtech.wisdom.tourism.systemconfig.simulation.dto.hotel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.SimulationCommonDto;
import lombok.Data;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * 酒店评价 模拟数据结构
 *
 * @date 2021/8/21 13:46
 * @author horadirm
 */
@Data
public class SimulationHotelDTO extends SimulationCommonDto {

    /**
     * 日评价初始数
     */
    private Integer dayOfInitCount;

    /**
     * 评分初始数
     */
    private BigDecimal initRate;

    /**
     * 日累计评价量
     */
    @JsonIgnore
    private Integer dayOfTotalCount;

    /**
     * 评分
     */
    private BigDecimal rate;

    /**
     * 月累计评价量
     */
    private BigDecimal monthOfEvaluateTotal;

    /**
     * 好评占比初始值（%）
     */
    private BigDecimal initGoodRatePercent;

    /**
     * 评价类型分布-好评
     */
    @JsonIgnore
    private BigDecimal goodRatePercent;

    /**
     * 评价类型分布-中评
     */
    @JsonIgnore
    private BigDecimal mediumRatePercent;

    /**
     * 评价类型分布-差评
     */
    @JsonIgnore
    private BigDecimal badRatePercent;

    /**
     * 评论热词词频
     */
    @Size(max = 5, message = "热词数量不能超过5个")
    private List<BaseVO> hotTagRank;

    /**
     * 常见房型均价初始值
     */
    private List<BaseVO> roomTypePrice;

    /**
     * 历史最高价
     */
    private BigDecimal highestPrice;

    /**
     * 历史最低价
     */
    private BigDecimal lowestPrice;

    /**
     * 历史平均价
     */
    @JsonIgnore
    private BigDecimal averagePrice;

}
