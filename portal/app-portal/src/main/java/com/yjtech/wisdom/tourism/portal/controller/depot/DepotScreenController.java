package com.yjtech.wisdom.tourism.portal.controller.depot;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AnalysisUtils;
import com.yjtech.wisdom.tourism.resource.depot.entity.vo.DepotBaseVo;
import com.yjtech.wisdom.tourism.resource.depot.entity.vo.DepotScreenUseVo;
import com.yjtech.wisdom.tourism.resource.depot.entity.vo.DepotTrendVo;
import com.yjtech.wisdom.tourism.resource.depot.entity.vo.DepotUseVo;
import com.yjtech.wisdom.tourism.resource.depot.query.DepotPageSummaryQuery;
import com.yjtech.wisdom.tourism.resource.depot.query.DepotSummaryQuery;
import com.yjtech.wisdom.tourism.resource.depot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 停车场
 *
 * @author zc
 * @since 2021-07-02
 */
@RestController
@RequestMapping("/depot/screen")
public class DepotScreenController {

    @Autowired
    private DepotService depotService;
    @Autowired
    private DepotSourceSummaryService sourceSummaryService;
    @Autowired
    private DepotTrendSummaryService trendSummaryService;
    @Autowired
    private DepotStaySummaryService staySummaryService;
    @Autowired
    private DepotTypeSummaryService typeSummaryService;

    /**
     * 停车场使用情况（首页）
     * @Param:
     * @return: JsonResult
     * @Author: zc
     * @Date: 2021-07-05
     */
    @GetMapping("/depotUseDetails")
    public JsonResult<DepotScreenUseVo> depotUseDetails(){
        return JsonResult.success(depotService.depotUseDetails());
    }
    /**
     * 停车场使用率
     * @Param:
     * @return: JsonResult
     * @Author: zc
     * @Date: 2021-07-05
     */
    @GetMapping("/depotUse")
    public JsonResult<DepotUseVo> depotUse(){
        return JsonResult.success(depotService.depotUse());
    }

    /**
     * 地图分布
     * @Param:  来源 0-市 1-省
     * @return: JsonResult
     * @Author: zc
     * @Date: 2021-07-05
     */
    @PostMapping("/queryDistributionMaps")
    public JsonResult<List<DepotBaseVo>> queryDistributionMaps(@RequestBody DepotSummaryQuery query){
        return JsonResult.success(sourceSummaryService.queryDistributionMaps(query));
    }

    /**
     * 车辆来源省份
     * @Param:  query
     * @return:  JsonResult
     * @Author: zc
     * @Date: 2021-07-05
     */
    @PostMapping("/querySourceOfProvince")
    public JsonResult<IPage<DepotBaseVo>> querySourceOfProvince(@RequestBody @Valid DepotPageSummaryQuery query){
        return JsonResult.success(sourceSummaryService.querySourceOfProvince(query));
    }

    /**
     * 车辆来源地市
     * @Param: query
     * @return: List<DepotBaseDto>
     * @Author: zc
     * @Date: 2021-07-05
     */
    @PostMapping("/querySourceOfCity")
    public JsonResult<IPage<DepotBaseVo>> querySourceOfCity(@RequestBody @Valid DepotPageSummaryQuery query) {
        return JsonResult.success(sourceSummaryService.querySourceOfCity(query));
    }

    /**
     * 停留时长
     * @Param: timeType 1-年 2-月 3-周  4-日
     * @return: JsonResult
     * @Author: zc
     * @Date: 2021-07-05
     */
    @PostMapping("/queryDepotStay")
    public JsonResult<List<BaseValueVO>> queryDepotStay(@RequestBody @Valid DepotSummaryQuery query) {
        return JsonResult.success(staySummaryService.queryType(query));
    }

    /**
     * 车辆类型分布
     * @Param: query
     * @return: JsonResult
     * @Author: zc
     * @Date: 2021-07-05
     */
    @PostMapping("/queryDepotType")
    public JsonResult<List<BasePercentVO>> queryDepotType(@RequestBody @Valid DepotSummaryQuery query) {
        return JsonResult.success(typeSummaryService.queryType(query));
    }

    /**
     * 车辆出入趋势
     *
     * @Param: query
     * @return: JsonResult
     * @Author: zc
     * @Date: 2021-07-05
     */
    @PostMapping("/queryDepotTrend")
    public JsonResult<List<BaseValueVO>> queryDepotTrend(@RequestBody DepotSummaryQuery query) {
        List<DepotTrendVo> depotTrendVos = trendSummaryService.getBaseMapper().queryTrend(query);
        return JsonResult.success(AnalysisUtils.MultipleBuildAnalysis(query, depotTrendVos, DepotTrendVo::getEnterQuantity, DepotTrendVo::getExitQuantity));
    }
}
