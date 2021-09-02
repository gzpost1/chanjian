package com.yjtech.wisdom.tourism.integration.pojo.bo;

import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.fxdist.FxDistOrderStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelAreaVisitStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelVisitStatisticsBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 一码游模拟数据 BO
 *
 * @date 2021/9/1 14:11
 * @author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OneTravelSimulationDataBO implements Serializable {

    private static final long serialVersionUID = -3455417327948534236L;

    /**
     * 查询一码游访问次数-综合总览
     */
    private Integer oneTravelVisitIndex;

    /**
     * 查询一码游交易-综合总览
     */
    private FxDistOrderStatisticsBO oneTravelTradeIndex;

    /**
     * 查询一码游投诉
     */
    private Integer oneTravelComplaint;

    /**
     * 查询一码游投诉趋势-一码游投诉
     */
    private List<AnalysisBaseInfo> oneTravelComplaintAnalysis;

    /**
     * 查询一码游受理状态分布-一码游投诉
     */
    private List<BasePercentVO> oneTravelComplaintDistribution;

    /**
     * 查询全国用户分布-一码游统计
     */
    private List<OneTravelAreaVisitStatisticsBO> userNationDistribution;

    /**
     * 查询访问数据-一码游统计
     */
    private OneTravelVisitStatisticsBO visitStatistics;

    /**
     * 查询交易数据-一码游统计
     */
    private FxDistOrderStatisticsBO oneTravelTradeStatistics;

    /**
     * 查询用户年龄分布-一码游统计
     */
    private List<BasePercentVO> userAgeDistribution;

    /**
     * 运营数据-一码游统计
     */
    private OperationDataInfo operationStatistics;

    /**
     * 商品订单分布-一码游统计
     */
    private List<BasePercentVO> orderFromProductTypeDistribution;

    /**
     * 商品交易额分布-一码游统计
     */
    private List<BasePercentVO> orderSumFromProductTypeDistribution;

    /**
     * 本年订单趋势-一码游统计
     */
    private List<AnalysisBaseInfo> orderAnalysis;

    /**
     * 本年交易额趋势-一码游统计
     */
    private List<AnalysisBaseInfo> orderSumAnalysis;

}
