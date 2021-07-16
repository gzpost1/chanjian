package com.yjtech.wisdom.tourism.portal.controller.ticket;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AnalysisUtils;
import com.yjtech.wisdom.tourism.extension.BizScenario;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.extension.ExtensionExecutor;
import com.yjtech.wisdom.tourism.resource.ticket.extensionpoint.TicketExtensionConstant;
import com.yjtech.wisdom.tourism.resource.ticket.extensionpoint.TicketQryExtPt;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketRankingQuery;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketSaleQuantityQuery;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketSumaryQuery;
import com.yjtech.wisdom.tourism.resource.ticket.vo.SaleTrendVO;
import com.yjtech.wisdom.tourism.resource.ticket.vo.TicketRankingVO;
import com.yjtech.wisdom.tourism.resource.ticket.vo.VisitorSourceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 票务
 *
 * @author xulei
 * @since 2021-07-02
 */
@RestController
@RequestMapping("/ticket/screen")
public class TicketController {

    @Autowired
    private ExtensionExecutor extensionExecutor;

    /**
     * 查询售票趋势
     * <p>
     * 月度售票趋势对比与售票趋势统计  使用这个接口
     *
     * @param query
     * @return
     */
    @PostMapping("/querySaleTrend")
    public JsonResult<List<BaseValueVO>> querySaleTrend(@RequestBody @Valid TicketSumaryQuery query) {
        // 定义业务+用例+场景
        List<SaleTrendVO> saleTrendVos = extensionExecutor.execute(TicketQryExtPt.class, buildBizScenario(query.getIsSimulation()),
                extension -> extension.querySaleTrend(query));
        return JsonResult.success(AnalysisUtils.MultipleBuildAnalysis(query, saleTrendVos, SaleTrendVO::getSaleQuantity));
    }


    /**
     * 检票时间分布
     *
     * @param query
     * @return
     */
    @PostMapping("/queryVisitTrend")
    public JsonResult<List<BaseValueVO>> queryVisitTrend(@RequestBody @Valid TicketSumaryQuery query) {
        List<SaleTrendVO> saleTrendVOS = extensionExecutor.execute(TicketQryExtPt.class, buildBizScenario(query.getIsSimulation()),
                extension -> extension.queryVisitTrend(query));
        List<BaseValueVO> baseVOs = AnalysisUtils.MultipleBuildAnalysis(query, saleTrendVOS, SaleTrendVO::getVisitQuantity);
        List<BigDecimal> rate = Lists.newArrayList();
        for (BaseValueVO baseValueVO : baseVOs) {
            if (!"coordinate".equals(baseValueVO.getName())) {
                List<Integer> quantity = baseValueVO.getValue().stream().map(vo -> Integer.valueOf(String.valueOf(vo))).collect(Collectors.toList());
                BigDecimal sum = new BigDecimal(String.valueOf(quantity.stream().reduce(Integer::sum).orElse(0)));
                BigDecimal zero = new BigDecimal("0");
                if (zero.compareTo(sum) == 0) {
                    rate = quantity.stream().map(vo -> zero
                    ).collect(Collectors.toList());
                } else {
                    rate = quantity.stream().map(vo -> {
                        {
                            BigDecimal a = new BigDecimal(String.valueOf(vo));
                            return a.divide(sum, 4, BigDecimal.ROUND_DOWN);
                        }
                    }).collect(Collectors.toList());
                }
            }
        }
        baseVOs.add(BaseValueVO.builder().name("rate").value(rate).build());
        return JsonResult.success(baseVOs);
    }


    /**
     * 售票数量统计
     *
     * @return
     */
    @PostMapping(path = "querySaleQuantity")
    public JsonResult<JSONObject> querySaleQuantity(@RequestBody TicketSaleQuantityQuery query) {
        return JsonResult.success(extensionExecutor.execute(TicketQryExtPt.class, buildBizScenario(query.getIsSimulation()),
                extension -> extension.querySaleQuantity(query)));
    }

    /**
     * 今日售票
     *
     * @return
     */
    @PostMapping(value = "queryFareCollection")
    public JsonResult<List<BaseVO>> queryFareCollection(@RequestBody TicketSaleQuantityQuery query) {
        return JsonResult.success(extensionExecutor.execute(TicketQryExtPt.class, buildBizScenario(query.getIsSimulation()),
                extension -> extension.queryFareCollection(query)));
    }

    /**
     * 来源地分布
     *
     * @param query
     * @return
     */
    @PostMapping(value = "queryVisitorSource")
    public JsonResult<VisitorSourceInfo> queryVisitorSource(@RequestBody TicketSumaryQuery query) {
        return JsonResult.success(extensionExecutor.execute(TicketQryExtPt.class, buildBizScenario(query.getIsSimulation()),
                extension -> extension.queryVisitorSource(query)));
    }

    /**
     * 游客来源省份排名
     *
     * @param query
     * @return
     */
    @PostMapping(value = "queryVisitorSourceRankingByProvince")
    public JsonResult<IPage<TicketRankingVO>> queryVisitorSourceRankingByProvince(@RequestBody TicketRankingQuery query) {
        return JsonResult.success(extensionExecutor.execute(TicketQryExtPt.class, buildBizScenario(query.getIsSimulation()),
                extension -> extension.queryVisitorSourceRankingByProvince(query)));
    }

    /**
     * 游客来源地市排名
     *
     * @param query
     * @return
     */
    @PostMapping(value = "queryVisitorSourceRankingByCity")
    public JsonResult<IPage<TicketRankingVO>> queryVisitorSourceRankingByCity(@RequestBody TicketRankingQuery query) {
        return JsonResult.success(extensionExecutor.execute(TicketQryExtPt.class, buildBizScenario(query.getIsSimulation()),
                extension -> extension.queryVisitorSourceRankingByCity(query)));
    }

    /**
     * 售票渠道排名
     *
     * @param query
     * @return
     */
    @PostMapping(value = "queryTicketChannelRanking")
    public JsonResult<IPage<TicketRankingVO>> queryTicketChannelRanking(@RequestBody TicketRankingQuery query) {
        return JsonResult.success(extensionExecutor.execute(TicketQryExtPt.class, buildBizScenario(query.getIsSimulation()),
                extension -> extension.queryTicketChannelRanking(query)));
    }

    /**
     * 票种排名
     *
     * @param query
     * @return
     */
    @PostMapping(value = "queryTicketModelRanking")
    public JsonResult<IPage<TicketRankingVO>> queryTicketModelRanking(@RequestBody TicketRankingQuery query) {
        return JsonResult.success(extensionExecutor.execute(TicketQryExtPt.class, buildBizScenario(query.getIsSimulation()),
                extension -> extension.queryTicketModelRanking(query)));
    }

    private BizScenario buildBizScenario(int isSimulation) {
        return BizScenario.valueOf(ExtensionConstant.BIZ_TICKET, TicketExtensionConstant.USE_CASE_STATISTIC
                , isSimulation == 0 ? ExtensionConstant.SCENARIO_IMPL : ExtensionConstant.SCENARIO_MOCK);
    }

}
