package com.yjtech.wisdom.tourism.portal.controller.decision;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.decisionsupport.business.dto.DecisionWarnWrapperDto;
import com.yjtech.wisdom.tourism.decisionsupport.business.service.DecisionSupportScreenService;
import com.yjtech.wisdom.tourism.decisionsupport.business.vo.DecisionWarnPageVo;
import com.yjtech.wisdom.tourism.decisionsupport.business.vo.DecisionWarnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 决策辅助
 *
 * @author renguangqian
 * @date 2021/8/5 19:20
 */
@RestController
@RequestMapping("decision")
public class DecisionSupportMangerController {

    @Autowired
    private DecisionSupportScreenService decisionSupportScreenService;

    /**
     * 查询决策预警_分页
     *
     * @param vo
     * @return
     */
    @PostMapping("queryPageDecisionWarn")
    public JsonResult<IPage<DecisionWarnWrapperDto>> queryPageDecisionWarn (@RequestBody @Validated DecisionWarnPageVo vo) {
        return JsonResult.success(decisionSupportScreenService.queryPageDecisionWarn(vo));
    }

    /**
     * 查询决策预警_分页
     *
     * @param vo
     * @return
     */
    @PostMapping("queryDecisionWarnList")
    public JsonResult<DecisionWarnWrapperDto> queryDecisionWarnList (@RequestBody @Validated DecisionWarnVo vo) {
        return JsonResult.success(decisionSupportScreenService.queryDecisionWarnList(vo));
    }

    /**
     * 查询决策预警_分页
     *
     * @return
     */
    @GetMapping("analyzeDecisionWarn")
    public JsonResult<DecisionWarnWrapperDto> analyzeDecisionWarn () {
        decisionSupportScreenService.analyzeDecisionWarn();
        return JsonResult.success();
    }
}
