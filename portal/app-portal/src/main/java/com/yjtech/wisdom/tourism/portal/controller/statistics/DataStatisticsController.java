package com.yjtech.wisdom.tourism.portal.controller.statistics;

import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.project.service.TbProjectInfoService;
import com.yjtech.wisdom.tourism.project.service.TbProjectLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 大屏-数据统计
 */
@RestController
@RequestMapping("/screen/statistics")
public class DataStatisticsController {

    @Autowired
    private TbProjectInfoService projectInfoService;
    @Autowired
    private TbProjectLabelService projectLabelService;

    /**
     * 大屏-数据统计-平台项目累计总数
     */
    @GetMapping("/queryProjectNumTrend")
    public JsonResult<List<BaseValueVO>> queryProjectNumTrend() {
        return JsonResult.success(projectInfoService.queryProjectNumTrend());
    }

    /**
     * 大屏-数据统计-各区县招商项目数量分布
     */
    @GetMapping("/queryAreaProjectNumPie")
    public JsonResult<List<BaseVO>> queryAreaProjectNumPie() {
        return JsonResult.success(projectInfoService.getBaseMapper().queryAreaProjectNumPie());
    }

    /**
     * 大屏-数据统计-项目特色标签项目数占比
     */
    @GetMapping("/queryProjectLabelTrend")
    public JsonResult<List<BaseVO>> queryProjectLabelTrend() {
        return JsonResult.success(projectLabelService.getBaseMapper().queryProjectLabelTrend());
    }

    /**
     * 大屏-数据统计-项目规划占地面积项目数分布
     */
    @GetMapping("/queryProjectPlanFootprintPie")
    public JsonResult<List<BaseVO>> queryProjectPlanFootprintPie() {
        return JsonResult.success(projectInfoService.getBaseMapper().queryProjectPlanFootprintPie());
    }

    /**
     * 大屏-数据统计-总投资额量级项目分布
     */
    @GetMapping("/queryInvestmentTotalTrend")
    public JsonResult<List<BaseVO>> queryInvestmentTotalTrend() {
        return JsonResult.success(projectInfoService.getBaseMapper().queryInvestmentTotalTrend());
    }

    /**
     * 大屏-数据统计-月度总投资额与引资金额需求趋势
     */
    @GetMapping("/queryProjectAmountTrend")
    public JsonResult<List<BaseValueVO>> queryProjectAmountTrend() {
        return JsonResult.success(projectInfoService.queryProjectAmountTrend());
    }
}
