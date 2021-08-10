package com.yjtech.wisdom.tourism.portal.controller.decision;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.decisionsupport.base.dto.TargetDto;
import com.yjtech.wisdom.tourism.decisionsupport.base.dto.WarnConfigDto;
import com.yjtech.wisdom.tourism.decisionsupport.base.service.TargetQueryService;
import com.yjtech.wisdom.tourism.decisionsupport.base.vo.WarnConfigVo;
import com.yjtech.wisdom.tourism.decisionsupport.business.dto.DecisionDto;
import com.yjtech.wisdom.tourism.decisionsupport.business.service.DecisionSupportAdminService;
import com.yjtech.wisdom.tourism.decisionsupport.business.vo.DecisionPageVo;
import com.yjtech.wisdom.tourism.decisionsupport.business.vo.DecisionVo;
import com.yjtech.wisdom.tourism.decisionsupport.business.vo.DeleteDecisionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 决策辅助
 *
 * @author renguangqian
 * @date 2021/8/5 14:16
 */
@RestController
@RequestMapping("decision")
public class DecisionSupportMangerController {

    @Autowired
    private DecisionSupportAdminService decisionSupportAdminService;

    @Autowired
    private TargetQueryService targetQueryService;

    /**
     * 查询决策_分页
     *
     * @param vo
     * @return
     */
    @PostMapping("queryPageDecision")
    public JsonResult<IPage<DecisionDto>> queryPageDecision(@RequestBody @Validated DecisionPageVo vo) {
        return JsonResult.success(decisionSupportAdminService.queryPageDecision(vo));
    }

    /**
     * 删除决策
     *
     * @param vo
     * @return
     */
    @PostMapping("delete")
    public JsonResult deleteDecision(@RequestBody @Validated DeleteDecisionVo vo) {
        decisionSupportAdminService.deleteDecision(vo);
        return JsonResult.success();
    }

    /**
     * 新增决策
     *
     * @param vo
     * @return
     */
    @PostMapping("creat")
    public JsonResult insertDecision(@RequestBody @Validated DecisionVo vo) {
        decisionSupportAdminService.insertDecision(vo);
        return JsonResult.success();
    }

    /**
     * 修改决策
     *
     * @param vo
     * @return
     */
    @PostMapping("update")
    public JsonResult updateDecision(@RequestBody @Validated DecisionVo vo) {
        decisionSupportAdminService.updateDecision(vo);
        return JsonResult.success();
    }

    /**
     * 指标列表查询
     *
     * @return
     */
    @GetMapping("queryTarget")
    public JsonResult<List<TargetDto>> queryTarget() {
        return JsonResult.success(targetQueryService.queryTarget());
    }

    /**
     * 指标列表查询
     *
     * @param vo
     * @return
     */
    @PostMapping("queryWarnConfig")
    public JsonResult<List<WarnConfigDto>> queryWarnConfig(WarnConfigVo vo) {
        return JsonResult.success(targetQueryService.queryWarnConfig(vo));
    }


}
