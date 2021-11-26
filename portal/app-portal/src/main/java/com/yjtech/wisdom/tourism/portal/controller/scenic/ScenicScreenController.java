package com.yjtech.wisdom.tourism.portal.controller.scenic;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.extension.BizScenario;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.extension.ExtensionExecutor;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateListDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.resource.scenic.entity.vo.ScenicBaseVo;
import com.yjtech.wisdom.tourism.resource.scenic.entity.vo.ScenicScreenVo;
import com.yjtech.wisdom.tourism.resource.scenic.extensionpoint.ScenicExtensionConstant;
import com.yjtech.wisdom.tourism.resource.scenic.extensionpoint.ScenicQryExtPt;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicScreenQuery;
import com.yjtech.wisdom.tourism.resource.scenic.service.ScenicService;
import com.yjtech.wisdom.tourism.resource.video.entity.TbVideoEntity;
import com.yjtech.wisdom.tourism.resource.video.service.TbVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.ObjectUtils.isNull;


/**
 * 景区大屏
 *
 * @author zc
 * @since 2021-08-03
 */
@RestController
@RequestMapping("/scenic/screen")
public class ScenicScreenController {

    @Autowired
    private ScenicService scenicService;
    @Autowired
    private TbVideoService videoService;
    @Resource
    private ExtensionExecutor extensionExecutor;
    /**
     * 景区分布——分页查询
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<ScenicScreenVo>> queryScreenForPage(@RequestBody ScenicScreenQuery query) {
        return JsonResult.success(scenicService.queryScreenForPage(query));
    }


    /**
     * 景区分布——景区等级分布
     *
     * @Param:
     * @return:
     */
    @PostMapping("/queryLevelDistribution")
    public JsonResult<List<BaseVO>> queryLevelDistribution() {
        return JsonResult.success(scenicService.queryLevelDistribution());
    }

    /**
     * 景区分布——评论列表
     *
     * @Param:
     * @return:
     */
    @PostMapping("/queryCommentForPage")
    public JsonResult<IPage<MarketingEvaluateListDTO>> queryCommentForPage(@RequestBody @Valid ScenicScreenQuery query) {
        return JsonResult.success(scenicService.queryCommentForPage(query));
    }

    /**
     * 查询评价热词排行
     *
     * @param query
     * @return
     */
    @PostMapping("queryHotRank")
    public JsonResult<List<BaseVO>> queryScenicHotRank(@RequestBody @Valid ScenicScreenQuery query){
        List<BaseVO> execute = extensionExecutor.execute(ScenicQryExtPt.class,
                buildBizScenario(ScenicExtensionConstant.SCENIC_QUANTITY, query.getIsSimulation()),
                extension -> extension.queryScenicHotRank(query));
        return JsonResult.success(execute);
    }

    /**
     * 景区分布——景区介绍
     *
     * @Param: idParam
     * @return:
     */
    @PostMapping("/queryIntroduce")
    public JsonResult<ScenicScreenVo> queryIntroduce(@RequestBody @Valid ScenicScreenQuery query) {
        return JsonResult.success(scenicService.queryIntroduce(query));
    }

    /**
     * 景区分布——游客接待量
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/queryTouristReception")
    public JsonResult<List<BaseVO>> queryTouristReception(@RequestBody @Valid ScenicScreenQuery query) {
        if (isNull(query.getBeginTime()) || isNull(query.getEndTime())) {
            throw new CustomException("统计时间不能为空");
        }
        List<BaseVO> execute = extensionExecutor.execute(ScenicQryExtPt.class,
                buildBizScenario(ScenicExtensionConstant.SCENIC_QUANTITY, query.getIsSimulation()),
                extension -> extension.queryTouristReception(query));
        return JsonResult.success(execute);
    }

    /**
     * 景区分布——视频监控(只需要传景区id和分页信息)
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/queryVideoByScenicId")
    public JsonResult<IPage<TbVideoEntity>> queryVideoByScenicId(@RequestBody ScenicScreenQuery query) {
        if (isNull(query) || isNull(query.getScenicId())) {
            throw new CustomException("景区id不能为空");
        }
        return JsonResult.success(videoService.queryVideoByScenicId(query));
    }

    /**
     * 景区分布——客流趋势(只需要传type)
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/queryPassengerFlowTrend")
    public JsonResult<List<BaseValueVO>> queryPassengerFlow(@RequestBody @Valid ScenicScreenQuery query) {
        List<BaseValueVO> execute = extensionExecutor.execute(ScenicQryExtPt.class,
                buildBizScenario(ScenicExtensionConstant.SCENIC_QUANTITY, query.getIsSimulation()),
                extension -> extension.queryPassengerFlow(query));
        return JsonResult.success(execute);
    }

    /**
     * 景区分布——查询景区评价类型分布
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/queryEvaluateTypeDistribution")
    public JsonResult<List<BasePercentVO>> queryEvaluateTypeDistribution(@RequestBody @Valid ScenicScreenQuery query) {
        if (isNull(query.getBeginTime()) || isNull(query.getEndTime())) {
            throw new CustomException("统计时间不能为空");
        }
        List<BasePercentVO> execute = extensionExecutor.execute(ScenicQryExtPt.class,
                buildBizScenario(ScenicExtensionConstant.SCENIC_QUANTITY, query.getIsSimulation()),
                extension -> extension.queryEvaluateTypeDistribution(query));
        return JsonResult.success(execute);
    }

    /**
     * 景区分布——游客关注及美誉度
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/queryScenicEvaluateStatistics")
    public JsonResult<MarketingEvaluateStatisticsDTO> queryScenicEvaluateStatistics(@RequestBody @Valid ScenicScreenQuery query) {
        MarketingEvaluateStatisticsDTO execute = extensionExecutor.execute(ScenicQryExtPt.class,
                buildBizScenario(ScenicExtensionConstant.SCENIC_QUANTITY, query.getIsSimulation()),
                extension -> extension.queryScenicEvaluateStatistics(query));
        return JsonResult.success(execute);
    }

    /**
     * 景区大数据——客流排行
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/queryPassengerFlowTop5")
    public JsonResult<IPage<ScenicBaseVo>> queryPassengerFlowTop5(@RequestBody @Valid ScenicScreenQuery query) {
        IPage<ScenicBaseVo> page = extensionExecutor.execute(ScenicQryExtPt.class,
                buildBizScenario(ScenicExtensionConstant.SCENIC_QUANTITY, query.getIsSimulation()),
                extension -> extension.queryPassengerFlowTop5(query));
        List<ScenicBaseVo> records = page.getRecords();
        double sum = records.stream().mapToDouble(value -> Double.parseDouble(value.getValue())).sum();
        records.forEach(scenicBaseVo -> {
            double value = Double.parseDouble(scenicBaseVo.getValue());
            BigDecimal percent = MathUtil.divide(BigDecimal.valueOf(value), BigDecimal.valueOf(sum));
            scenicBaseVo.setPercent(percent.doubleValue());
        });
        return JsonResult.success(page);
    }

    /**
     * 景区大数据——评价排行TOP5
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/queryEvaluateTop5")
    public JsonResult<IPage<BaseVO>> queryEvaluateTop5(@RequestBody @Valid ScenicScreenQuery query) {
        IPage<BaseVO> page = extensionExecutor.execute(ScenicQryExtPt.class,
                buildBizScenario(ScenicExtensionConstant.SCENIC_QUANTITY, query.getIsSimulation()),
                extension -> extension.queryEvaluateTop5(query));
        return JsonResult.success(page);
    }

    /**
     * 景区大数据——满意度排行
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/querySatisfactionTop5")
    public JsonResult<IPage<ScenicBaseVo>> querySatisfactionTop5(@RequestBody @Valid ScenicScreenQuery query) {
        IPage<ScenicBaseVo> page = extensionExecutor.execute(ScenicQryExtPt.class,
                buildBizScenario(ScenicExtensionConstant.SCENIC_QUANTITY, query.getIsSimulation()),
                extension -> extension.querySatisfactionTop5(query));
        return JsonResult.success(page);
    }

    /**
     * 景区分布——热度趋势
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/queryHeatTrend")
    public JsonResult<List<BaseValueVO>> queryHeatTrend(@RequestBody @Valid ScenicScreenQuery query) {
        List<BaseValueVO> execute = extensionExecutor.execute(ScenicQryExtPt.class,
                buildBizScenario(ScenicExtensionConstant.SCENIC_QUANTITY, query.getIsSimulation()),
                extension -> extension.queryHeatTrend(query));
        return JsonResult.success(execute);
    }

    /**
     * 景区分布——满意度趋势
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/querySatisfactionTrend")
    public JsonResult<List<BaseValueVO>> querySatisfactionTrend(@RequestBody @Valid ScenicScreenQuery query) {
        List<BaseValueVO> execute = extensionExecutor.execute(ScenicQryExtPt.class,
                buildBizScenario(ScenicExtensionConstant.SCENIC_QUANTITY, query.getIsSimulation()),
                extension -> extension.querySatisfactionTrend(query));
        return JsonResult.success(execute);
    }

    private BizScenario buildBizScenario(String useCasePraiseType, Byte isSimulation) {
        return BizScenario.valueOf(ExtensionConstant.SCENIC, useCasePraiseType,
                EntityConstants.NO.equals(isSimulation) ? ExtensionConstant.SCENARIO_IMPL : ExtensionConstant.SCENARIO_MOCK);
    }
}
