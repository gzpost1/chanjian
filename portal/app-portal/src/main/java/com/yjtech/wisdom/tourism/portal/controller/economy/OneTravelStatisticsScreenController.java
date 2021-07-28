package com.yjtech.wisdom.tourism.portal.controller.economy;

import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelVisitStatisticsBO;
import com.yjtech.wisdom.tourism.integration.service.OneTravelApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 一码游统计 大屏
 *
 * @Author horadirm
 * @Date 2021/7/27 17:22
 */
@RestController
@RequestMapping("/oneTravelStatistics/screen/")
public class OneTravelStatisticsScreenController {

    @Autowired
    private OneTravelApiService oneTravelApiService;

    /**
     * 全国用户分布
     * @return
     */
    @PostMapping("queryProvinceVisitStatistics")
    public JsonResult<List<OneTravelVisitStatisticsBO>> queryProvinceVisitStatistics() {
        return JsonResult.success(oneTravelApiService.queryProvinceVisitStatistics());
    }






}
