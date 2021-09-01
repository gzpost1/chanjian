package com.yjtech.wisdom.tourism.portal.controller.economy;

import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.SimulationBaseVO;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.extension.BizScenario;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.extension.ExtensionExecutor;
import com.yjtech.wisdom.tourism.integration.extensionpoint.OneTravelExtensionConstant;
import com.yjtech.wisdom.tourism.integration.extensionpoint.OneTravelQryExtPt;
import com.yjtech.wisdom.tourism.integration.pojo.bo.OperationDataInfo;
import com.yjtech.wisdom.tourism.integration.pojo.bo.fxdist.FxDistOrderStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelAreaVisitStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelVisitStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.FxDistQueryVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

    @Resource
    private ExtensionExecutor extensionExecutor;


    /**
     * 查询全国用户分布
     * @return
     */
    @PostMapping("queryUserNationDistribution")
    public JsonResult<List<OneTravelAreaVisitStatisticsBO>> queryUserNationDistribution(@RequestBody @Valid SimulationBaseVO vo) {
        return JsonResult.success(extensionExecutor.execute(OneTravelQryExtPt.class,
                buildOneTravelBizScenario(OneTravelExtensionConstant.ONE_TRAVEL_QUANTITY, vo.getIsSimulation()),
                OneTravelQryExtPt::queryUserNationDistribution));
    }

    /**
     * 查询访问数据
     * @return
     */
    @PostMapping("queryVisitStatistics")
    public JsonResult<OneTravelVisitStatisticsBO> queryVisitStatistics(@RequestBody @Valid SimulationBaseVO vo) {
        return JsonResult.success(extensionExecutor.execute(OneTravelQryExtPt.class,
                buildOneTravelBizScenario(OneTravelExtensionConstant.ONE_TRAVEL_QUANTITY, vo.getIsSimulation()),
                OneTravelQryExtPt::queryVisitStatistics));
    }

    /**
     * 查询交易数据
     * @return
     */
    @PostMapping("queryOrderStatistics")
    public JsonResult<FxDistOrderStatisticsBO> queryOrderStatistics(@RequestBody @Valid FxDistQueryVO vo) {
        return JsonResult.success(extensionExecutor.execute(OneTravelQryExtPt.class,
                buildOneTravelBizScenario(OneTravelExtensionConstant.ONE_TRAVEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryOneTravelTradeStatistics(vo)));
    }

    /**
     * 查询用户年龄分布
     * @return
     */
    @PostMapping("queryUserAgeDistribution")
    public JsonResult<List<BasePercentVO>> queryUserAgeDistribution(@RequestBody @Valid SimulationBaseVO vo) {
        return JsonResult.success(extensionExecutor.execute(OneTravelQryExtPt.class,
                buildOneTravelBizScenario(OneTravelExtensionConstant.ONE_TRAVEL_QUANTITY, vo.getIsSimulation()),
                OneTravelQryExtPt::queryUserAgeDistribution));
    }

    /**
     * 查询运营数据
     * @return
     */
    @PostMapping("queryOperationStatistics")
    public JsonResult<OperationDataInfo> queryOperationStatistics(@RequestBody @Valid SimulationBaseVO vo) {
        return JsonResult.success(extensionExecutor.execute(OneTravelQryExtPt.class,
                buildOneTravelBizScenario(OneTravelExtensionConstant.ONE_TRAVEL_QUANTITY, vo.getIsSimulation()),
                OneTravelQryExtPt::queryOperationStatistics));
    }

    /**
     * 查询商品订单分布
     * @return
     */
    @PostMapping("queryOrderFromProductTypeDistribution")
    public JsonResult<List<BasePercentVO>> queryOrderFromProductTypeDistribution(@RequestBody @Valid FxDistQueryVO vo) {
        return JsonResult.success(extensionExecutor.execute(OneTravelQryExtPt.class,
                buildOneTravelBizScenario(OneTravelExtensionConstant.ONE_TRAVEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryOrderFromProductTypeDistribution(vo)));
    }

    /**
     * 查询商品交易额分布
     * @return
     */
    @PostMapping("queryOrderSumFromProductTypeDistribution")
    public JsonResult<List<BasePercentVO>> queryOrderSumFromProductTypeDistribution(@RequestBody @Valid FxDistQueryVO vo) {
        return JsonResult.success(extensionExecutor.execute(OneTravelQryExtPt.class,
                buildOneTravelBizScenario(OneTravelExtensionConstant.ONE_TRAVEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryOrderSumFromProductTypeDistribution(vo)));
    }

    /**
     * 查询本年订单趋势
     * @return
     */
    @PostMapping("queryOrderAnalysis")
    public JsonResult<List<AnalysisBaseInfo>> queryOrderAnalysis(@RequestBody @Valid FxDistQueryVO vo) {
        return JsonResult.success(extensionExecutor.execute(OneTravelQryExtPt.class,
                buildOneTravelBizScenario(OneTravelExtensionConstant.ONE_TRAVEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryOrderAnalysis(vo)));
    }

    /**
     * 查询本年交易额趋势
     * @return
     */
    @PostMapping("queryOrderSumAnalysis")
    public JsonResult<List<AnalysisBaseInfo>> queryOrderSumAnalysis(@RequestBody @Valid FxDistQueryVO vo) {
        return JsonResult.success(extensionExecutor.execute(OneTravelQryExtPt.class,
                buildOneTravelBizScenario(OneTravelExtensionConstant.ONE_TRAVEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryOrderSumAnalysis(vo)));
    }


    /**
     * 构建一码游业务扩展点
     * @param useCasePraiseType
     * @param isSimulation
     * @return
     */
    private BizScenario buildOneTravelBizScenario(String useCasePraiseType, Byte isSimulation) {
        return BizScenario.valueOf(ExtensionConstant.ONE_TRAVEL, useCasePraiseType
                , isSimulation == 0 ? ExtensionConstant.SCENARIO_IMPL : ExtensionConstant.SCENARIO_MOCK);
    }

}
