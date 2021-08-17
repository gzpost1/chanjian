package com.yjtech.wisdom.tourism.portal.controller.scenic;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.dto.MonthPassengerFlowDto;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.mybatis.utils.AnalysisUtils;
import com.yjtech.wisdom.tourism.resource.scenic.entity.vo.ScenicBaseVo;
import com.yjtech.wisdom.tourism.resource.scenic.entity.vo.ScenicScreenVo;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicPageQuery;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicScreenQuery;
import com.yjtech.wisdom.tourism.resource.scenic.service.ScenicService;
import com.yjtech.wisdom.tourism.resource.video.entity.TbVideoEntity;
import com.yjtech.wisdom.tourism.resource.video.service.TbVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    /**
     * 景区分布——分页查询
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<ScenicScreenVo>> queryScreenForPage(@RequestBody ScenicPageQuery query) {
        return JsonResult.success(scenicService.queryScreenForPage(query));
    }


    /**
     * 景区分布——景区等级分布
     *
     * @Param:
     * @return:
     */
    @PostMapping("/queryLevelDistribution")
    public JsonResult<List<ScenicBaseVo>> queryLevelDistribution() {
        return JsonResult.success(scenicService.queryLevelDistribution());
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
    public JsonResult<List<ScenicBaseVo>> queryTouristReception(@RequestBody @Valid ScenicScreenQuery query) {
        if (isNull(query.getBeginTime()) || isNull(query.getEndTime())) {
            throw new CustomException("统计时间不能为空");
        }
        return JsonResult.success(scenicService.queryTouristReception(query));
    }

    /**
     * 景区分布——视频监控(只需要传景区id和分页信息)
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/queryVideoByScenicId")
    public JsonResult<IPage<TbVideoEntity>> queryVideoByScenicId(@RequestBody ScenicPageQuery query) {
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
        if (query.getType() == 3 || query.getType() == 4) {
            throw new CustomException("时间类型只能传1或者2");
        }
        return JsonResult.success(AnalysisUtils.MultipleBuildAnalysis(
                query,
                scenicService.queryPassengerFlowTrend(query),
                true,
                MonthPassengerFlowDto::getNumber, MonthPassengerFlowDto::getTbNumber, MonthPassengerFlowDto::getHbScale, MonthPassengerFlowDto::getTbScale));
    }

    /**
     * 景区分布——查询景区评价类型分布
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/queryEvaluateTypeDistribution")
    public JsonResult<List<BasePercentVO>> queryEvaluateTypeDistribution(@RequestBody @Valid ScenicScreenQuery vo) {
        return JsonResult.success(scenicService.queryEvaluateTypeDistribution(vo));
    }

    /**
     * 景区分布——游客关注及美誉度
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/queryScenicEvaluateStatistics")
    public JsonResult<MarketingEvaluateStatisticsDTO> queryScenicEvaluateStatistics(@RequestBody @Valid ScenicScreenQuery vo) {
        return JsonResult.success(scenicService.queryScenicEvaluateStatistics(vo));
    }

    /**
     * 景区大数据——客流排行
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/queryPassengerFlowTop5")
    public JsonResult<IPage<ScenicBaseVo>> queryPassengerFlowTop5(@RequestBody @Valid ScenicPageQuery query) {
        return JsonResult.success(scenicService.queryPassengerFlowTop5(query));
    }

    /**
     * 景区大数据——评价排行
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/queryEvaluateTop5")
    public JsonResult<IPage<BaseVO>> queryEvaluateTop5(@RequestBody @Valid ScenicPageQuery query) {
        return JsonResult.success(scenicService.queryEvaluateTop5(query));
    }

    /**
     * 景区大数据——满意度排行
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/querySatisfactionTop5")
    public JsonResult<IPage<BaseVO>> querySatisfactionTop5(@RequestBody @Valid ScenicPageQuery query) {
        return JsonResult.success(scenicService.querySatisfactionTop5(query));
    }

    /**
     * 景区分布——热度趋势
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/queryHeatTrend")
    public JsonResult<List<MonthPassengerFlowDto>> queryHeatTrend(@RequestBody @Valid ScenicScreenQuery query) {
        return JsonResult.success(scenicService.queryHeatTrend(query));
    }

    /**
     * 景区分布——满意度趋势
     *
     * @Param: query
     * @return:
     */
    @PostMapping("/querySatisfactionTrend")
    public JsonResult<List<MonthPassengerFlowDto>> querySatisfactionTrend(@RequestBody @Valid ScenicScreenQuery query) {
        return JsonResult.success(scenicService.querySatisfactionTrend(query));
    }
}
