package com.yjtech.wisdom.tourism.portal.controller.marketing;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanUtils;
import com.yjtech.wisdom.tourism.extension.BizScenario;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.extension.ExtensionExecutor;
import com.yjtech.wisdom.tourism.marketing.extensionpoint.HotelExtensionConstant;
import com.yjtech.wisdom.tourism.marketing.extensionpoint.HotelQryExtPt;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.EvaluateSatisfactionRankDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateListDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateQueryVO;
import com.yjtech.wisdom.tourism.marketing.service.MarketingEvaluateService;
import com.yjtech.wisdom.tourism.resource.scenic.entity.vo.ScenicBaseVo;
import com.yjtech.wisdom.tourism.resource.scenic.extensionpoint.ScenicExtensionConstant;
import com.yjtech.wisdom.tourism.resource.scenic.extensionpoint.ScenicQryExtPt;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicScreenQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 大屏_评论分析
 *
 * @Author horadirm
 * @Date 2021/8/10 19:52
 */
@Slf4j
@RestController
@RequestMapping("/evaluate/data/")
public class EvaluateController {

    @Autowired
    private MarketingEvaluateService marketingEvaluateService;
    @Resource
    private ExtensionExecutor extensionExecutor;



    /**
     * 查询评价统计-酒店详情
     *
     * @param vo
     * @return
     */
    @PostMapping("queryEvaluateStatisticsDetail")
    public JsonResult<MarketingEvaluateStatisticsDTO> queryEvaluateStatisticsDetail(@RequestBody @Valid EvaluateQueryVO vo) {
        //设置默认评论状态-启用
        vo.setEquipStatus(Objects.isNull(vo.getEquipStatus()) ? EntityConstants.ENABLED : vo.getEquipStatus());

        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryEvaluateStatistics(vo)));
    }

    /**
     * 查询评价统计
     *
     * @param vo
     * @return
     */
    @PostMapping("queryEvaluateStatistics")
    public JsonResult<MarketingEvaluateStatisticsDTO> queryEvaluateStatistics(@RequestBody @Valid EvaluateQueryVO vo) {
        //设置默认评论状态-启用
        vo.setEquipStatus(Objects.isNull(vo.getEquipStatus()) ? EntityConstants.ENABLED : vo.getEquipStatus());

        //获取酒店
        MarketingEvaluateStatisticsDTO hotel = extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryEvaluateStatisticsBigData(vo));

        ScenicScreenQuery query = BeanUtils.copyBean(vo, ScenicScreenQuery.class);
        //获取景区
        MarketingEvaluateStatisticsDTO scenic = extensionExecutor.execute(ScenicQryExtPt.class,
                buildBizScenarioScenic(ScenicExtensionConstant.SCENIC_QUANTITY, query.getIsSimulation()),
                extension -> extension.queryScenicEvaluateStatistics(query));

        return JsonResult.success(new MarketingEvaluateStatisticsDTO(
                hotel.getEvaluateTotal() + scenic.getEvaluateTotal(),
                hotel.getSatisfaction().add(scenic.getSatisfaction()).divide(new BigDecimal(2), 1, BigDecimal.ROUND_HALF_UP),
                hotel.getRate().add(scenic.getRate()).divide(new BigDecimal(2), 1, BigDecimal.ROUND_HALF_UP)
        ));
    }

    /**
     * 查询评价类型分布
     *
     * @param vo
     * @return
     */
    @PostMapping("queryEvaluateTypeDistribution")
    public JsonResult<List<BasePercentVO>> queryEvaluateTypeDistribution(@RequestBody @Valid EvaluateQueryVO vo) {
        //设置默认评论状态-启用
        vo.setEquipStatus(Objects.isNull(vo.getEquipStatus()) ? EntityConstants.ENABLED : vo.getEquipStatus());

        //获取酒店民宿
        List<BasePercentVO> hotel = extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryEvaluateTypeDistributionBigData(vo));
        Map<String, Double> hotelMap = hotel.stream().collect(Collectors.toMap(BasePercentVO::getName, BasePercentVO::getRate));

        ScenicScreenQuery query = BeanUtils.copyBean(vo, ScenicScreenQuery.class);
        //获取景区
        List<BasePercentVO> scenic = extensionExecutor.execute(ScenicQryExtPt.class,
                buildBizScenarioScenic(ScenicExtensionConstant.SCENIC_QUANTITY, query.getIsSimulation()),
                extension -> extension.queryEvaluateTypeDistribution(query));
        Map<String, Double> scenicMap = scenic.stream().collect(Collectors.toMap(BasePercentVO::getName, BasePercentVO::getRate));

        //构建返回
        List<BasePercentVO> resultList = Lists.newArrayList();
        //计算好评
        double goodEvaluateResult = (hotelMap.get(SimulationConstants.GOOD_EVALUATE_DESCRIBE) + scenicMap.get(SimulationConstants.GOOD_EVALUATE_DESCRIBE)) / 2;
        resultList.add(new BasePercentVO(SimulationConstants.GOOD_EVALUATE_DESCRIBE, null,
                new BigDecimal(goodEvaluateResult).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()));
        //计算中评
        double mediumEvaluateResult = (hotelMap.get(SimulationConstants.MEDIUM_EVALUATE_DESCRIBE) + scenicMap.get(SimulationConstants.MEDIUM_EVALUATE_DESCRIBE)) / 2;
        resultList.add(new BasePercentVO(SimulationConstants.MEDIUM_EVALUATE_DESCRIBE, null,
                new BigDecimal(mediumEvaluateResult).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()));
        //计算差评
        resultList.add(new BasePercentVO(SimulationConstants.BAD_EVALUATE_DESCRIBE, null,
                new BigDecimal(100 - goodEvaluateResult - mediumEvaluateResult).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()));

        return JsonResult.success(resultList);
    }

    /**
     * 查询评价对象分布
     *
     * @param vo
     * @return
     */
    @PostMapping("queryEvaluateObjectDistribution")
    public JsonResult<List<BasePercentVO>> queryEvaluateObjectDistribution(@RequestBody @Valid EvaluateQueryVO vo) {
        //设置默认评论状态-启用
        vo.setEquipStatus(Objects.isNull(vo.getEquipStatus()) ? EntityConstants.ENABLED : vo.getEquipStatus());
        //模拟数据
        if(EntityConstants.YES.equals(vo.getIsSimulation())){
            ScenicScreenQuery query = BeanUtils.copyBean(vo, ScenicScreenQuery.class);
            //获取景区
            MarketingEvaluateStatisticsDTO scenic = extensionExecutor.execute(ScenicQryExtPt.class,
                    buildBizScenarioScenic(ScenicExtensionConstant.SCENIC_QUANTITY, query.getIsSimulation()),
                    extension -> extension.queryScenicEvaluateStatistics(query));
            //获取酒店民宿
            MarketingEvaluateStatisticsDTO hotel = extensionExecutor.execute(HotelQryExtPt.class,
                    buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                    extension -> extension.queryEvaluateStatisticsBigData(vo));
            //获取酒店民宿、景区合计
            Integer total = scenic.getEvaluateTotal() + hotel.getEvaluateTotal();

            //构建返回信息
            List<BasePercentVO> resultList = Lists.newArrayList();
            resultList.add(new BasePercentVO("景区", scenic.getEvaluateTotal().toString(), new BigDecimal(scenic.getEvaluateTotal() * 100).divide(new BigDecimal(total), 1, BigDecimal.ROUND_HALF_UP).doubleValue()));
            resultList.add(new BasePercentVO("酒店", hotel.getEvaluateTotal().toString(), new BigDecimal(hotel.getEvaluateTotal() * 100).divide(new BigDecimal(total), 1, BigDecimal.ROUND_HALF_UP).doubleValue()));

            return JsonResult.success(resultList);
        }

        return JsonResult.success(marketingEvaluateService.queryEvaluateObjectDistribution(vo));
    }

    /**
     * 查询评价热度（评价量）趋势、同比、环比
     *
     * @param vo
     * @return
     */
    @PostMapping("queryEvaluateAnalysis")
    public JsonResult<List<AnalysisBaseInfo>> queryEvaluateAnalysis(@RequestBody @Valid EvaluateQueryVO vo) {
        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryEvaluateAnalysis(vo)));
    }

    /**
     * 查询评价满意度趋势、同比、环比
     *
     * @param vo
     * @return
     */
    @PostMapping("queryEvaluateSatisfactionAnalysis")
    public JsonResult<List<AnalysisBaseInfo>> queryEvaluateSatisfactionAnalysis(@RequestBody @Valid EvaluateQueryVO vo) {
        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryEvaluateSatisfactionAnalysis(vo)));
    }

    /**
     * 查询评价热词排行
     *
     * @param vo
     * @return
     */
    @PostMapping("queryEvaluateHotRank")
    public JsonResult<List<BaseVO>> queryEvaluateHotRank(@RequestBody @Valid EvaluateQueryVO vo) {
        //设置默认评论状态-启用
        vo.setEquipStatus(Objects.isNull(vo.getEquipStatus()) ? EntityConstants.ENABLED : vo.getEquipStatus());

        if(StringUtils.isBlank(vo.getPlaceId())){
            //获取酒店民宿
            List<BaseVO> hotelHotList = extensionExecutor.execute(HotelQryExtPt.class,
                    buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                    extension -> extension.queryEvaluateHotRankBigData(vo));

            ScenicScreenQuery query = BeanUtils.copyBean(vo, ScenicScreenQuery.class);
            //获取景区
            List<BaseVO> scenicHotList = extensionExecutor.execute(ScenicQryExtPt.class,
                    buildBizScenarioScenic(ScenicExtensionConstant.SCENIC_QUANTITY, query.getIsSimulation()),
                    extension -> extension.queryScenicHotRank(query));

            hotelHotList.addAll(scenicHotList);
            //合并去重，降序排列
            List<BaseVO> resultList = hotelHotList.stream().collect(Collectors.toMap(BaseVO::getName,
                    item -> item,
                    (o1, o2) -> {
                        o1.setValue(String.valueOf(Integer.valueOf(o1.getValue()) + Integer.valueOf(o2.getValue())));
                        return o1;
                    })).values().stream().sorted(Comparator.comparing(BaseVO::getValue, Comparator.comparing(Integer::parseInt)).reversed()).collect(Collectors.toList());

            return JsonResult.success(resultList);
        }

        //获取酒店民宿详情
        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryEvaluateHotRank(vo)));
    }

    /**
     * 查询景区评论排行
     *
     * @Param: vo
     * @return:
     */
    @PostMapping("/queryEvaluateTop5")
    public JsonResult<IPage<BaseVO>> queryEvaluateTop5(@RequestBody @Valid ScenicScreenQuery vo) {
        IPage<BaseVO> page = extensionExecutor.execute(ScenicQryExtPt.class,
                buildBizScenarioScenic(ScenicExtensionConstant.SCENIC_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryEvaluateTop5(vo));
        return JsonResult.success(page);
    }

    /**
     * 查询景区满意度排行
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/querySatisfactionTop5")
    public JsonResult<IPage<ScenicBaseVo>> querySatisfactionTop5(@RequestBody @Valid ScenicScreenQuery vo) {
        IPage<ScenicBaseVo> page = extensionExecutor.execute(ScenicQryExtPt.class,
                buildBizScenarioScenic(ScenicExtensionConstant.SCENIC_QUANTITY, vo.getIsSimulation()),
                extension -> extension.querySatisfactionTop5(vo));
        return JsonResult.success(page);
    }

    /**
     * 查询酒店评价排行
     *
     * @param vo
     * @return
     */
    @PostMapping("queryHotelEvaluateRank")
    public JsonResult<IPage<BaseVO>> queryEvaluateRank(@RequestBody @Valid EvaluateQueryVO vo) {
        vo.buildStatus();
        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryEvaluateRank(vo)));
    }

    /**
     * 查询酒店满意度排行
     *
     * @param vo
     * @return
     */
    @PostMapping("queryHotelEvaluateSatisfactionRank")
    public JsonResult<IPage<EvaluateSatisfactionRankDTO>> queryEvaluateSatisfactionRank(@RequestBody @Valid EvaluateQueryVO vo) {
        vo.buildStatus();
        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryEvaluateSatisfactionRank(vo)));
    }

    /**
     * 查询评价分页列表
     *
     * @param vo
     * @return
     */
    @PostMapping("queryForPage")
    public JsonResult<IPage<MarketingEvaluateListDTO>> queryForPage(@RequestBody @Valid EvaluateQueryVO vo) {
        //设置默认评论状态-启用
        vo.setEquipStatus(Objects.isNull(vo.getEquipStatus()) ? EntityConstants.ENABLED : vo.getEquipStatus());

        return JsonResult.success(marketingEvaluateService.queryForPage(vo));
    }


    /**
     * 构建酒店业务扩展点
     * @param useCasePraiseType
     * @param isSimulation
     * @return
     */
    private BizScenario buildBizScenario(String useCasePraiseType, Byte isSimulation) {
        return BizScenario.valueOf(ExtensionConstant.HOTEL, useCasePraiseType,
                EntityConstants.NO.equals(isSimulation) ? ExtensionConstant.SCENARIO_IMPL : ExtensionConstant.SCENARIO_MOCK);
    }

    /**
     * 构建景区业务扩展点
     * @param useCasePraiseType
     * @param isSimulation
     * @return
     */
    private BizScenario buildBizScenarioScenic(String useCasePraiseType, Byte isSimulation) {
        return BizScenario.valueOf(ExtensionConstant.SCENIC, useCasePraiseType,
                EntityConstants.NO.equals(isSimulation) ? ExtensionConstant.SCENARIO_IMPL : ExtensionConstant.SCENARIO_MOCK);
    }

}
