package com.yjtech.wisdom.tourism.portal.controller.tour;

import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.service.DistrictTourService;
import com.yjtech.wisdom.tourism.service.mock.DistrictTourMockService;
import com.yjtech.wisdom.tourism.vo.DistrictMockRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 游客结构 - 模拟数据
 *
 * @author renguangqian
 * @date 2021/8/12 9:56
 */
@RestController
@RequestMapping("district/mock")
public class DistrictTourMockController {

    @Autowired
    private DistrictTourService districtTourService;

    @Autowired
    private DistrictTourMockService districtTourMockService;

    /**
     * 模拟数据规则 - 新增/修改
     *
     * @param vo
     * @return
     */
    @PostMapping("saveMockRule")
    public JsonResult saveMockRule(@RequestBody @Validated DistrictMockRuleVo vo) {
        districtTourService.saveMockRule(vo);
        return JsonResult.success();
    }

    /**
     * 模拟数据规则 - 查询
     *
     * @return
     */
    @GetMapping("queryMockRule")
    public JsonResult queryMockRule() {
        return JsonResult.success(districtTourMockService.getMockRule());
    }

    /**
     * 模拟数据 - 刷新
     *
     * @param
     * @return
     */
    @GetMapping("refresh")
    public JsonResult refresh() {
        districtTourMockService.refreshRandom();
        return JsonResult.success();
    }
}
