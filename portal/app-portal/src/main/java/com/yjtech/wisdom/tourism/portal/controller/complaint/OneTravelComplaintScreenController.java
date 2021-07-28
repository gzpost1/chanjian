package com.yjtech.wisdom.tourism.portal.controller.complaint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelComplaintListBO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.OneTravelQueryVO;
import com.yjtech.wisdom.tourism.integration.service.OneTravelApiService;
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
        return JsonResult.success(oneTravelApiService.queryComplaintForPage(vo));
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




}
