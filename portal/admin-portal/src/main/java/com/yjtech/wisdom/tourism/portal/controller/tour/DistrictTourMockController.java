package com.yjtech.wisdom.tourism.portal.controller.tour;

import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.service.DistrictTourService;
import com.yjtech.wisdom.tourism.vo.DistrictMockRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 模拟数据规则 - 新增
     *
     * @param vo
     * @return
     */
    @PostMapping("saveMockRule")
    public JsonResult saveMockRule(@RequestBody @Validated DistrictMockRuleVo vo) {
        districtTourService.saveMockRule(vo);
        return JsonResult.success();
    }
}
