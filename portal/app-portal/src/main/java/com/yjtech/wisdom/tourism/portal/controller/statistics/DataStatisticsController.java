package com.yjtech.wisdom.tourism.portal.controller.statistics;

import com.yjtech.wisdom.tourism.common.annotation.IgnoreAuth;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.project.dto.ProjectInvestmentDto;
import com.yjtech.wisdom.tourism.project.dto.ProjectInvestmentStaticDto;
import com.yjtech.wisdom.tourism.project.service.TbProjectInfoService;
import com.yjtech.wisdom.tourism.project.service.TbProjectLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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
    @IgnoreAuth
    public JsonResult<List<BaseValueVO>> queryProjectNumTrend() {
        return JsonResult.success(projectInfoService.queryProjectNumTrend());
    }

    /**
     * 大屏-数据统计-各区县招商项目数量分布
     */
    @GetMapping("/queryAreaProjectNumPie")
    @IgnoreAuth
    public JsonResult<List<BaseVO>> queryAreaProjectNumPie() {
        return JsonResult.success(projectInfoService.getBaseMapper().queryAreaProjectNumPie());
    }

    /**
     * 大屏-数据统计-项目特色标签项目数占比
     */
    @GetMapping("/queryProjectLabelTrend")
    @IgnoreAuth
    public JsonResult<List<BaseVO>> queryProjectLabelTrend() {
        return JsonResult.success(projectLabelService.getBaseMapper().queryProjectLabelTrend());
    }

    /**
     * 大屏-数据统计-项目规划占地面积项目数分布
     */
    @GetMapping("/queryProjectPlanFootprintPie")
    @IgnoreAuth
    public JsonResult<List<BaseVO>> queryProjectPlanFootprintPie() {
        return JsonResult.success(projectInfoService.getBaseMapper().queryProjectPlanFootprintPie());
    }

    /**
     * 大屏-数据统计-总投资额量级项目分布
     */
    @GetMapping("/queryInvestmentTotalTrend")
    @IgnoreAuth
    public JsonResult<ProjectInvestmentStaticDto> queryInvestmentTotalTrend() {
        ProjectInvestmentStaticDto dto = new ProjectInvestmentStaticDto();
        List<ProjectInvestmentDto> projectInvestmentDtos = projectInfoService.getBaseMapper().queryInvestmentTotalTrend();
        double totalMoney = projectInvestmentDtos.stream().mapToDouble(d -> Double.parseDouble(d.getMoney())).sum();
        projectInvestmentDtos.stream().forEach(projectInvestmentDto -> {
            double money = Double.parseDouble(projectInvestmentDto.getMoney());
            BigDecimal percent = MathUtil.calPercent(BigDecimal.valueOf(money), BigDecimal.valueOf(totalMoney), 2);
            projectInvestmentDto.setScale(percent.toString());
        });

        dto.setList(projectInvestmentDtos);
        dto.setTotalMoney(projectInfoService.getBaseMapper().getInvestmentTotal(null));
        dto.setCompareMoney(projectInfoService.getCompareMoney());
        return JsonResult.success(dto);
    }

    /**
     * 大屏-数据统计-月度总投资额与引资金额需求趋势
     */
    @GetMapping("/queryProjectAmountTrend")
    @IgnoreAuth
    public JsonResult<List<BaseValueVO>> queryProjectAmountTrend() {
        return JsonResult.success(projectInfoService.queryProjectAmountTrend());
    }

    /**
     * 大屏-底部-注册公司、投资项目、规划项目占地统计
     */
    @GetMapping("/queryDataStatic")
    @IgnoreAuth
    public JsonResult<List<BaseVO>> queryDataStatic() {
        return JsonResult.success(projectInfoService.queryDataStatic());
    }
}