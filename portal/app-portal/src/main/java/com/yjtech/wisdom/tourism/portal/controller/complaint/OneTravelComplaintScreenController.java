package com.yjtech.wisdom.tourism.portal.controller.complaint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.enums.AnalysisDateTypeEnum;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelComplaintListBO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.OneTravelQueryVO;
import com.yjtech.wisdom.tourism.integration.service.OneTravelApiService;
import com.yjtech.wisdom.tourism.system.domain.IconSpotEnum;
import com.yjtech.wisdom.tourism.system.service.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 一码游投诉 大屏
 *
 * @Author horadirm
 * @Date 2021/7/21 9:38
 */
@RestController
@RequestMapping("/oneTravelComplaint/screen/")
public class OneTravelComplaintScreenController {

    @Autowired
    private OneTravelApiService oneTravelApiService;
    @Autowired
    private IconService iconService;

    /**
     * 查询投诉总量
     * @param vo
     * @return
     */
    @PostMapping("queryTotal")
    public JsonResult queryTotal(@RequestBody @Valid OneTravelQueryVO vo) {
        return JsonResult.success(oneTravelApiService.queryComplaintStatistics(vo));
    }

    /**
     * 查询投诉列表/地图点位
     * @param vo
     * @return
     */
    @PostMapping("queryComplaintForPage")
    public JsonResult<IPage<OneTravelComplaintListBO>> queryComplaintForPage(@RequestBody @Valid OneTravelQueryVO vo) {
        IPage<OneTravelComplaintListBO> page = oneTravelApiService.queryComplaintForPage(vo);
        //通过配置缓存设置iconUrl
        for (OneTravelComplaintListBO oneTravelComplaintListBO : page.getRecords()){
            oneTravelComplaintListBO.setIconUrl(iconService.queryIconUrl(IconSpotEnum.ONE_TRAVEL_COMPLAINT, oneTravelComplaintListBO.getComplaintStatus()));
        }
        return JsonResult.success(page);
    }

    /**
     * 查询受理状态分布
     * @param vo
     * @return
     */
    @PostMapping("queryComplaintDistribution")
    public JsonResult<List<BasePercentVO>> queryComplaintDistribution(@RequestBody @Valid OneTravelQueryVO vo) {
        return JsonResult.success(oneTravelApiService.queryComplaintDistribution(vo));
    }

    /**
     * 查询一码游本年投诉趋势
     * @param vo
     * @return
     */
    @PostMapping("queryComplaintAnalysis")
    public JsonResult<List<AnalysisBaseInfo>> queryComplaintAnalysis(@RequestBody @Valid OneTravelQueryVO vo) {
        //设置sql时间格式类型
        vo.setSqlDateFormat(AnalysisDateTypeEnum.getItemByValue(vo.getType()).getSqlDateFormat());
        return JsonResult.success(oneTravelApiService.queryComplaintAnalysis(vo));
    }




}
