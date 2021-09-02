package com.yjtech.wisdom.tourism.integration.extensionpoint;

import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.extension.ExtensionPointI;
import com.yjtech.wisdom.tourism.integration.pojo.bo.OperationDataInfo;
import com.yjtech.wisdom.tourism.integration.pojo.bo.fxdist.FxDistOrderStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelAreaVisitStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelVisitStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.FxDistQueryVO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.OneTravelQueryVO;
import com.yjtech.wisdom.tourism.mybatis.entity.IndexQueryVO;

import java.util.List;

/**
 * 一码游相关
 *
 * @author horadirm
 * @date 2021/8/22 12:38
 */
public interface OneTravelQryExtPt extends ExtensionPointI {


    /**
     * 查询一码游访问次数-综合总览
     *
     * @param vo
     * @return
     */
    Integer queryOneTravelVisitIndex(IndexQueryVO vo);

    /**
     * 查询一码游交易-综合总览
     *
     * @param vo
     * @return
     */
    FxDistOrderStatisticsBO queryOneTravelTradeIndex(FxDistQueryVO vo);

    /**
     * 查询一码游投诉
     *
     * @param vo
     * @return
     */
    Integer queryOneTravelComplaint(OneTravelQueryVO vo);

    /**
     * 查询一码游投诉趋势-一码游投诉
     *
     * @param vo
     * @return
     */
    List<AnalysisBaseInfo> queryOneTravelComplaintAnalysis(OneTravelQueryVO vo);

    /**
     * 查询一码游受理状态分布-一码游投诉
     *
     * @param vo
     * @return
     */
    List<BasePercentVO> queryOneTravelComplaintDistribution(OneTravelQueryVO vo);

    /**
     * 查询全国用户分布-一码游统计
     *
     * @return
     */
    List<OneTravelAreaVisitStatisticsBO> queryUserNationDistribution();

    /**
     * 查询访问数据-一码游统计
     *
     * @return
     */
    OneTravelVisitStatisticsBO queryVisitStatistics();

    /**
     * 查询交易数据-一码游统计
     *
     * @param vo
     * @return
     */
    FxDistOrderStatisticsBO queryOneTravelTradeStatistics(FxDistQueryVO vo);

    /**
     * 查询用户年龄分布-一码游统计
     *
     * @return
     */
    List<BasePercentVO> queryUserAgeDistribution();

    /**
     * 运营数据-一码游统计
     *
     * @return
     */
    OperationDataInfo queryOperationStatistics();

    /**
     * 商品订单分布-一码游统计
     *
     * @param vo
     * @return
     */
    List<BasePercentVO> queryOrderFromProductTypeDistribution(FxDistQueryVO vo);

    /**
     * 商品交易额分布-一码游统计
     *
     * @param vo
     * @return
     */
    List<BasePercentVO> queryOrderSumFromProductTypeDistribution(FxDistQueryVO vo);

    /**
     * 本年订单趋势-一码游统计
     *
     * @param vo
     * @return
     */
    List<AnalysisBaseInfo> queryOrderAnalysis(FxDistQueryVO vo);

    /**
     * 本年交易额趋势-一码游统计
     *
     * @param vo
     * @return
     */
    List<AnalysisBaseInfo> queryOrderSumAnalysis(FxDistQueryVO vo);

}
