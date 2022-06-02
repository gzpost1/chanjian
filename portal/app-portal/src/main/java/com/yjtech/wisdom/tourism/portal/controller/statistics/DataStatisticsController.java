package com.yjtech.wisdom.tourism.portal.controller.statistics;

import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.project.service.TbProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 大屏-项目
 */
@RestController
@RequestMapping("/screen/statistics")
public class DataStatisticsController {

    @Autowired
    private TbProjectInfoService projectInfoService;

    /**
     * 大屏-平台项目累计总数
     * @Param:
     * @return:
     */
    @GetMapping("/queryProjectNumTrend")
    public JsonResult<List<BaseValueVO>> queryProjectNumTrend() {
        return JsonResult.success(projectInfoService.queryProjectNumTrend());
    }
}
