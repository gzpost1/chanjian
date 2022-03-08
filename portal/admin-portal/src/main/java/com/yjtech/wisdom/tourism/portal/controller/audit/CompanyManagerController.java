package com.yjtech.wisdom.tourism.portal.controller.audit;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.bigscreen.dto.AuditCompanyParam;
import com.yjtech.wisdom.tourism.bigscreen.dto.TbRegisterInfoParam;
import com.yjtech.wisdom.tourism.bigscreen.dto.UpdateBlacklistParam;
import com.yjtech.wisdom.tourism.bigscreen.entity.TbAuditInfoEntity;
import com.yjtech.wisdom.tourism.bigscreen.entity.TbRegisterInfoEntity;
import com.yjtech.wisdom.tourism.bigscreen.service.TbAuditInfoService;
import com.yjtech.wisdom.tourism.bigscreen.service.TbRegisterInfoService;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 客商库
 *
 * @author Mujun
 * @since 2022-03-04
 */
@Slf4j
@RestController
@RequestMapping("/company-manager")
public class CompanyManagerController {
    @Autowired
    private TbRegisterInfoService registerInfoService;

    @Autowired
    private TbAuditInfoService auditInfoService;


    /**
     * 企业详情
     *
     * @param idParam
     * @return
     */
    @PostMapping("/queryForDetail")
    public JsonResult<TbRegisterInfoEntity> queryForDetail(
            @RequestBody @Valid IdParam idParam) {
        return JsonResult.success(registerInfoService.getById(idParam.getId()));
    }

    /**
     * 企业管理 分页查询
     *
     * @param params
     * @return
     */
    @PostMapping("/queryForPage")
    public JsonResult<Page<TbRegisterInfoEntity>> queryForPage(
            @RequestBody @Valid TbRegisterInfoParam params) {
        return JsonResult.success(registerInfoService.page(params));
    }

    /**
     * 修改黑名单状态
     *
     * @param params
     * @return
     */
    @PostMapping("/updateBlacklist")
    public JsonResult updateBlacklist(
            @RequestBody @Valid UpdateBlacklistParam params) {
        TbRegisterInfoEntity queryRegisterInfoEntity = registerInfoService.getById(params.getId());
        AssertUtil.isFalse(Objects.isNull(queryRegisterInfoEntity), "该企业不存在");
        queryRegisterInfoEntity.setBlacklist(params.getBlacklist());
        registerInfoService.updateById(queryRegisterInfoEntity);
        return JsonResult.success(registerInfoService.updateById(queryRegisterInfoEntity));
    }

    /**
     * 审核
     *
     * @param params
     * @return
     */
    @PostMapping("/auditCompany")
    public JsonResult auditCompany(
            @RequestBody @Valid AuditCompanyParam params) {
        TbRegisterInfoEntity queryRegisterInfoEntity = registerInfoService.getById(params.getId());
        AssertUtil.isFalse(Objects.isNull(queryRegisterInfoEntity), "该企业不存在");
        queryRegisterInfoEntity.setAuditStatus(params.getAuditStatus());
        queryRegisterInfoEntity.setAuditTime(LocalDateTime.now());
        TbAuditInfoEntity auditInfoEntity = TbAuditInfoEntity.builder().companyId(queryRegisterInfoEntity.getId()).auditStatus(params.getAuditStatus()).auditComment(params.getAuditComment()).build();
        registerInfoService.updateById(queryRegisterInfoEntity);
        auditInfoService.save(auditInfoEntity);
        return JsonResult.success("审核成功");
    }

    /**
     * 审批记录
     *
     * @param idParam
     * @return
     */
    @PostMapping("/auditRecord")
    public JsonResult auditRecord(
            @RequestBody @Valid IdParam idParam) {
        TbRegisterInfoEntity queryRegisterInfoEntity = registerInfoService.getById(idParam.getId());
        AssertUtil.isFalse(Objects.isNull(queryRegisterInfoEntity), "该企业不存在");
        List<TbAuditInfoEntity> list = auditInfoService.queryAuditRecord(idParam.getId());
        return JsonResult.success(list);
    }


}
