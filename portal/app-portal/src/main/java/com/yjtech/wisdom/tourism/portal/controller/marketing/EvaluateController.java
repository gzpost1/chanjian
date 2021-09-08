package com.yjtech.wisdom.tourism.portal.controller.marketing;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

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
     * 查询评价统计
     *
     * @param vo
     * @return
     */
    @PostMapping("queryEvaluateStatistics")
    public JsonResult<MarketingEvaluateStatisticsDTO> queryEvaluateStatistics(@RequestBody @Valid EvaluateQueryVO vo) {
        //设置默认评论状态-启用
        vo.setEquipStatus(Objects.isNull(vo.getEquipStatus()) ? EntityConstants.ENABLED : vo.getEquipStatus());

        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryEvaluateStatisticsBigData(vo)));
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

        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryEvaluateTypeDistributionBigData(vo)));
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
        return JsonResult.success(marketingEvaluateService.queryEvaluateAnalysis(vo));
    }

    /**
     * 查询评价满意度趋势、同比、环比
     *
     * @param vo
     * @return
     */
    @PostMapping("queryEvaluateSatisfactionAnalysis")
    public JsonResult<List<AnalysisBaseInfo>> queryEvaluateSatisfactionAnalysis(@RequestBody @Valid EvaluateQueryVO vo) {
        return JsonResult.success(marketingEvaluateService.queryEvaluateSatisfactionAnalysis(vo));
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
    public JsonResult<IPage<BaseVO>> queryEvaluateTop5(@RequestBody @Valid EvaluateQueryVO vo) {
        vo.buildStatus();
        return JsonResult.success(marketingEvaluateService.queryEvaluateTop5(vo));
    }

    /**
     * 查询景区满意度排行
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/querySatisfactionTop5")
    public JsonResult<IPage<EvaluateSatisfactionRankDTO>> querySatisfactionTop5(@RequestBody @Valid EvaluateQueryVO vo) {
        vo.buildStatus();
        return JsonResult.success(marketingEvaluateService.querySatisfactionTop5(vo));
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
        return JsonResult.success(marketingEvaluateService.queryEvaluateRank(vo));
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
        return JsonResult.success(marketingEvaluateService.queryEvaluateSatisfactionRank(vo));
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
     * 构建业务扩展点
     * @param useCasePraiseType
     * @param isSimulation
     * @return
     */
    private BizScenario buildBizScenario(String useCasePraiseType, Byte isSimulation) {
        return BizScenario.valueOf(ExtensionConstant.HOTEL, useCasePraiseType
                , isSimulation == 0 ? ExtensionConstant.SCENARIO_IMPL : ExtensionConstant.SCENARIO_MOCK);
    }

}
