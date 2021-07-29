package com.yjtech.wisdom.tourism.portal.controller.economy;

import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.integration.pojo.bo.OperationDataInfo;
import com.yjtech.wisdom.tourism.integration.pojo.bo.fxdist.FxDistOrderStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelAreaVisitStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelVisitStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.FxDistQueryVO;
import com.yjtech.wisdom.tourism.integration.service.FxDistApiService;
import com.yjtech.wisdom.tourism.integration.service.OneTravelApiService;
import com.yjtech.wisdom.tourism.integration.service.SmartTravelApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 一码游统计 大屏
 *
 * @Author horadirm
 * @Date 2021/7/27 17:22
 */
@RestController
@RequestMapping("/oneTravelStatistics/screen/")
public class OneTravelStatisticsScreenController {

    @Autowired
    private OneTravelApiService oneTravelApiService;
    @Autowired
    private FxDistApiService fxDistApiService;
    @Autowired
    private SmartTravelApiService smartTravelApiService;


    /**
     * 查询全国用户分布
     * @return
     */
    @PostMapping("queryUserNationDistribution")
    public JsonResult<List<OneTravelAreaVisitStatisticsBO>> queryUserNationDistribution() {
        return JsonResult.success(oneTravelApiService.queryProvinceVisitStatistics());
    }

    /**
     * 查询访问数据
     * @return
     */
    @PostMapping("queryVisitStatistics")
    public JsonResult<OneTravelVisitStatisticsBO> queryVisitStatistics() {
        return JsonResult.success(oneTravelApiService.queryVisitStatistics());
    }

    /**
     * 查询交易数据
     * @return
     */
    @PostMapping("queryOrderStatistics")
    public JsonResult<FxDistOrderStatisticsBO> queryOrderStatistics(@RequestBody @Valid FxDistQueryVO vo) {
        return JsonResult.success(fxDistApiService.queryOrderStatistics(vo));
    }

    /**
     * 查询用户年龄分布
     * @return
     */
    @PostMapping("queryUserAgeDistribution")
    public JsonResult<List<BasePercentVO>> queryUserAgeDistribution() {
        return JsonResult.success(oneTravelApiService.queryUserAgeDistribution());
    }

    /**
     * 查询运营数据
     * @return
     */
    @PostMapping("queryOperationStatistics")
    public JsonResult<OperationDataInfo> queryOperationStatistics() {
        //获取入驻景点
        Integer scenicCount = smartTravelApiService.queryScenicCountByArea(null);
        //获取入驻商户（店铺数）
        Integer storeCount = fxDistApiService.queryStoreCountByArea(null);
        //获取商品总量
        Integer productCount = fxDistApiService.queryProductCountByArea(null);

        return JsonResult.success(new OperationDataInfo(scenicCount, storeCount, productCount));
    }

    /**
     * 查询商品订单分布
     * @return
     */
    @PostMapping("queryOrderFromProductTypeDistribution")
    public JsonResult<List<BasePercentVO>> queryOrderFromProductTypeDistribution(@RequestBody @Valid FxDistQueryVO vo) {
        return JsonResult.success(fxDistApiService.queryOrderFromProductTypeDistribution(vo));
    }

    /**
     * 查询商品交易额分布
     * @return
     */
    @PostMapping("queryOrderSumFromProductTypeDistribution")
    public JsonResult<List<BasePercentVO>> queryOrderSumFromProductTypeDistribution(@RequestBody @Valid FxDistQueryVO vo) {
        return JsonResult.success(fxDistApiService.queryOrderSumFromProductTypeDistribution(vo));
    }

    /**
     * 查询本年订单趋势
     * @return
     */
    @PostMapping("queryOrderAnalysis")
    public JsonResult<List<AnalysisBaseInfo>> queryOrderAnalysis(@RequestBody @Valid FxDistQueryVO vo) {
        return JsonResult.success(fxDistApiService.queryOrderAnalysis(vo));
    }

    /**
     * 查询本年交易额趋势
     * @return
     */
    @PostMapping("queryOrderSumAnalysis")
    public JsonResult<List<AnalysisBaseInfo>> queryOrderSumAnalysis(@RequestBody @Valid FxDistQueryVO vo) {
        return JsonResult.success(fxDistApiService.queryOrderSumAnalysis(vo));
    }

}
