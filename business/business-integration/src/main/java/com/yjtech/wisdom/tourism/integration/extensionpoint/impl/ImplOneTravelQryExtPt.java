package com.yjtech.wisdom.tourism.integration.extensionpoint.impl;

import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.integration.extensionpoint.OneTravelExtensionConstant;
import com.yjtech.wisdom.tourism.integration.extensionpoint.OneTravelQryExtPt;
import com.yjtech.wisdom.tourism.integration.pojo.bo.OperationDataInfo;
import com.yjtech.wisdom.tourism.integration.pojo.bo.fxdist.FxDistOrderStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelAreaVisitStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelVisitStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.FxDistQueryVO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.OneTravelQueryVO;
import com.yjtech.wisdom.tourism.integration.service.FxDistApiService;
import com.yjtech.wisdom.tourism.integration.service.OneTravelApiService;
import com.yjtech.wisdom.tourism.integration.service.SmartTravelApiService;
import com.yjtech.wisdom.tourism.mybatis.entity.IndexQueryVO;
import com.yjtech.wisdom.tourism.mybatis.entity.SimulationBaseVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 一码游相关 真实数据 扩展
 *
 * @author horadirm
 * @date 2021/8/21 10:44
 */
@Extension(bizId = ExtensionConstant.ONE_TRAVEL,
        useCase = OneTravelExtensionConstant.ONE_TRAVEL_QUANTITY,
        scenario = ExtensionConstant.SCENARIO_IMPL)
public class ImplOneTravelQryExtPt implements OneTravelQryExtPt {

    @Autowired
    private FxDistApiService fxDistApiService;
    @Autowired
    private OneTravelApiService oneTravelApiService;
    @Autowired
    private SmartTravelApiService smartTravelApiService;


    /**
     * 查询一码游访问次数-综合总览
     * @param vo
     * @return
     */
    @Override
    public Integer queryOneTravelVisitIndex(IndexQueryVO vo) {
        OneTravelVisitStatisticsBO visitStatistics = oneTravelApiService.queryVisitStatistics();
        return visitStatistics.getTodayActiveUser().intValue();
    }

    /**
     * 查询一码游交易-综合总览
     * @param vo
     * @return
     */
    @Override
    public FxDistOrderStatisticsBO queryOneTravelTradeIndex(FxDistQueryVO vo) {
        return fxDistApiService.queryOrderStatistics(vo);
    }

    /**
     * 查询一码游投诉
     * @param vo
     * @return
     */
    @Override
    public Integer queryOneTravelComplaint(OneTravelQueryVO vo) {
        return oneTravelApiService.queryComplaintStatistics(vo);
    }

    /**
     * 查询一码游投诉趋势-一码游投诉
     * @param vo
     * @return
     */
    @Override
    public List<AnalysisBaseInfo> queryOneTravelComplaintAnalysis(OneTravelQueryVO vo) {
        return oneTravelApiService.queryComplaintAnalysis(vo);
    }

    /**
     * 查询一码游受理状态分布-一码游投诉
     * @param vo
     * @return
     */
    @Override
    public List<BasePercentVO> queryOneTravelComplaintDistribution(OneTravelQueryVO vo) {
        return oneTravelApiService.queryComplaintDistribution(vo);
    }

    /**
     * 查询全国用户分布-一码游统计
     * @return
     */
    @Override
    public List<OneTravelAreaVisitStatisticsBO> queryUserNationDistribution(SimulationBaseVO vo) {
        return oneTravelApiService.queryProvinceVisitStatistics();
    }

    /**
     * 查询访问数据-一码游统计
     * @return
     */
    @Override
    public OneTravelVisitStatisticsBO queryVisitStatistics(SimulationBaseVO vo) {
        return oneTravelApiService.queryVisitStatistics();
    }

    /**
     * 查询交易数据-一码游统计
     * @param vo
     * @return
     */
    @Override
    public FxDistOrderStatisticsBO queryOneTravelTradeStatistics(FxDistQueryVO vo) {
        return fxDistApiService.queryOrderStatistics(vo);
    }

    /**
     * 查询用户年龄分布-一码游统计
     * @return
     */
    @Override
    public List<BasePercentVO> queryUserAgeDistribution(SimulationBaseVO vo) {
        return oneTravelApiService.queryUserAgeDistribution();
    }

    /**
     * 运营数据-一码游统计
     * @return
     */
    @Override
    public OperationDataInfo queryOperationStatistics(SimulationBaseVO vo) {
        //获取入驻景点
        Integer scenicCount = smartTravelApiService.queryScenicCountByArea(null);
        //获取入驻商户（店铺数）
        Integer storeCount = fxDistApiService.queryStoreCountByArea(null);
        //获取商品总量
        Integer productCount = fxDistApiService.queryProductCountByArea(null);

        return new OperationDataInfo(scenicCount, storeCount, productCount);
    }

    /**
     * 商品订单分布-一码游统计
     * @param vo
     * @return
     */
    @Override
    public List<BasePercentVO> queryOrderFromProductTypeDistribution(FxDistQueryVO vo) {
        return fxDistApiService.queryOrderFromProductTypeDistribution(vo);
    }

    /**
     * 商品交易额分布-一码游统计
     * @param vo
     * @return
     */
    @Override
    public List<BasePercentVO> queryOrderSumFromProductTypeDistribution(FxDistQueryVO vo) {
        return fxDistApiService.queryOrderSumFromProductTypeDistribution(vo);
    }

    /**
     * 本年订单趋势-一码游统计
     * @param vo
     * @return
     */
    @Override
    public List<AnalysisBaseInfo> queryOrderAnalysis(FxDistQueryVO vo) {
        return fxDistApiService.queryOrderAnalysis(vo);
    }

    /**
     * 本年交易额趋势-一码游统计
     * @param vo
     * @return
     */
    @Override
    public List<AnalysisBaseInfo> queryOrderSumAnalysis(FxDistQueryVO vo) {
        return fxDistApiService.queryOrderSumAnalysis(vo);
    }
}
