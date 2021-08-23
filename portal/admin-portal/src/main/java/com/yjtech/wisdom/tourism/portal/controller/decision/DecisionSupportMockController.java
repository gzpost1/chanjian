package com.yjtech.wisdom.tourism.portal.controller.decision;

import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.decisionsupport.business.dto.DecisionMockDto;
import com.yjtech.wisdom.tourism.decisionsupport.business.service.DecisionSupportMockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 模拟数据填报
 *
 * @author renguangqian
 * @date 2021/8/23 16:06
 */
@RestController
@RequestMapping("decision/mock")
public class DecisionSupportMockController {

    @Autowired
    private DecisionSupportMockService decisionSupportMockService;

    /**
     * 模拟数据-列表查询
     *
     * @return
     */
    @GetMapping("queryList")
    public JsonResult<List<DecisionMockDto>> queryList() {
        return JsonResult.success(decisionSupportMockService.queryList());
    }
}
