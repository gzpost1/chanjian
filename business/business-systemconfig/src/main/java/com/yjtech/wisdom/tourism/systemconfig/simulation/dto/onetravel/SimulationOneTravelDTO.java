package com.yjtech.wisdom.tourism.systemconfig.simulation.dto.onetravel;

import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.SimulationCommonDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 一码游 模拟数据结构
 *
 * @date 2021/8/22 12:26
 * @author horadirm
 */
@Data
public class SimulationOneTravelDTO extends SimulationCommonDto {

    /**
     * 小时初始访问数
     */
    private Integer hourOfInitVisit;

    /**
     * 小时访问数
     */
    private Integer hourOfVisit;

    /**
     * 日累计访问数
     */
    private Integer dayOfTotalVisit;

    /**
     * 昨日访问数-系数
     */
    private BigDecimal yesterdayVisitCoefficient;

    /**
     * 昨日访问数-增加数
     */
    private Integer yesterdayVisitAdditions;

    /**
     * 昨日活跃用户数-系数
     */
    private BigDecimal yesterdayActiveCoefficient;

    /**
     * 昨日活跃用户数-增加数
     */
    private Integer yesterdayActiveAdditions;

    /**
     * 使用总人数-系数
     */
    private BigDecimal userTotalCoefficient;

    /**
     * 使用总人数-增加数
     */
    private Integer userTotalAdditions;

    /**
     * 总访问数-系数
     */
    private BigDecimal visitTotalCoefficient;

    /**
     * 总访问数-增加数
     */
    private Integer visitTotalAdditions;

    /**
     * 省外来源分布（%）
     */
    private List<BasePercentVO> provinceOutSideSourceDistribution;

    /**
     * 入驻景点数
     */
    private Integer scenic;

    /**
     * 入驻商户数
     */
    private Integer merchant;

    /**
     * 商品总量
     */
    private Integer productTotal;

    /**
     * 日订单量
     */
    private Integer dayOfOrderCount;

    /**
     * 月累计订单量
     */
    private Integer monthOfOrderCountTotal;

    /**
     * 日订单金额
     */
    private BigDecimal dayOfOrderSum;

    /**
     * 月累计订单金额
     */
    private BigDecimal monthOfOrderSumTotal;

    /**
     * 商品类型分布
     */
    private List<BasePercentVO> productTypeDistribution;

    /**
     * 日投诉量
     */
    private Integer dayOfComplaintCount;

    /**
     * 月累计投诉量
     */
    private Integer monthOfComplaintCountTotal;

    /**
     * 投诉类型分布
     */
    private List<BasePercentVO> complaintTypeDistribution;

}
