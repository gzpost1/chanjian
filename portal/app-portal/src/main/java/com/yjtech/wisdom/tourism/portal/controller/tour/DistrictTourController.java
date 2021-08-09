package com.yjtech.wisdom.tourism.portal.controller.tour;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.mybatis.utils.AnalysisUtils;
import com.yjtech.wisdom.tourism.dto.DataOverviewDto;
import com.yjtech.wisdom.tourism.dto.MonthPassengerFlowDto;
import com.yjtech.wisdom.tourism.dto.VisitorDto;
import com.yjtech.wisdom.tourism.service.DistrictTourService;
import com.yjtech.wisdom.tourism.vo.DataOverviewVo;
import com.yjtech.wisdom.tourism.vo.MonthPassengerFlowVo;
import com.yjtech.wisdom.tourism.vo.PassengerFlowVo;
import com.yjtech.wisdom.tourism.vo.VisitorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 游客结构
 *
 * @author renguangqian
 * @date 2021/7/29 19:12
 */
@RestController
@RequestMapping("district")
public class DistrictTourController {

    @Autowired
    private DistrictTourService service;

    /**
     * 查询游客总数据-数据总览
     *
     * @param vo
     * @return
     */
    @PostMapping("queryDataOverview")
    public JsonResult<DataOverviewDto> queryDataOverview (@RequestBody @Validated DataOverviewVo vo) {
        return JsonResult.success(service.queryDataOverview(vo));
    }

    /**
     * 游客来源（省、市）_分页查询
     *
     * @param vo
     * @return
     */
    @PostMapping("queryPageVisitor")
    public JsonResult<IPage<VisitorDto>> queryPageVisitor (@RequestBody @Validated VisitorVo vo) {
        return JsonResult.success(service.queryPageVisitor(vo));
    }

    /**
     * 本年客流趋势
     *
     * @param vo
     * @return
     */
    @PostMapping("queryYearPassengerFlow")
    public JsonResult<List<BaseValueVO>> queryYearPassengerFlow (@RequestBody @Validated PassengerFlowVo vo) {
        List<MonthPassengerFlowDto> yearPassengerFlowDtos = service.queryYearPassengerFlow(vo);
        yearPassengerFlowDtos = yearPassengerFlowDtos.stream().map(v -> {
            v.setTime(v.getDate());
            return v;
        }).collect(Collectors.toList());
        return JsonResult.success(AnalysisUtils.MultipleBuildAnalysis(
                vo,
                yearPassengerFlowDtos,
                true,
                MonthPassengerFlowDto::getNumber, MonthPassengerFlowDto::getTbNumber, MonthPassengerFlowDto::getHbScale, MonthPassengerFlowDto::getTbScale));
    }

    /**
     * 本月客流趋势
     *
     * @param vo
     * @return
     */
    @PostMapping("queryMonthPassengerFlow")
    public  JsonResult<List<BaseValueVO>> queryMonthPassengerFlow (@RequestBody @Validated MonthPassengerFlowVo vo) {
        List<MonthPassengerFlowDto> monthPassengerFlowDtos = service.queryMonthPassengerFlow(vo);
        monthPassengerFlowDtos = monthPassengerFlowDtos.stream().map(v -> {
            v.setTime(v.getDate());
            return v;
        }).collect(Collectors.toList());
        return JsonResult.success(AnalysisUtils.MultipleBuildAnalysis(
                vo,
                monthPassengerFlowDtos,
                true,
                MonthPassengerFlowDto::getNumber, MonthPassengerFlowDto::getTbNumber, MonthPassengerFlowDto::getHbScale, MonthPassengerFlowDto::getTbScale));
    }
}
