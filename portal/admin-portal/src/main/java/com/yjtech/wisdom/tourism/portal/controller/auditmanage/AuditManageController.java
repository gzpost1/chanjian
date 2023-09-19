package com.yjtech.wisdom.tourism.portal.controller.auditmanage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.resource.auditmanage.dto.AuditDto;
import com.yjtech.wisdom.tourism.resource.auditmanage.entity.AuditManageInfo;
import com.yjtech.wisdom.tourism.resource.auditmanage.entity.AuditManageLog;
import com.yjtech.wisdom.tourism.resource.auditmanage.entity.AuditManageProcess;
import com.yjtech.wisdom.tourism.resource.auditmanage.service.AuditManageInfoService;
import com.yjtech.wisdom.tourism.resource.auditmanage.service.AuditManageLogService;
import com.yjtech.wisdom.tourism.resource.auditmanage.service.AuditManageProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * 【后台】审核
 *
 * @author songjun
 * @since 2023/9/11
 */
@Slf4j
@RestController
@RequestMapping("/auditmanage")
public class AuditManageController {
    @Autowired
    private AuditManageProcessService processService;
    @Autowired
    private AuditManageLogService logService;
    @Autowired
    private AuditManageInfoService infoService;

    /**
     * 提交审核
     *
     * @param dto
     * @return
     */
    @PostMapping("commit")
    public JsonResult<Void> commit(@RequestBody @Valid AuditDto dto) {
        // 如果source审核失败或者不存在审核，则可以提交
        LambdaQueryWrapper<AuditManageInfo> infoQuery = Wrappers.lambdaQuery();
        infoQuery.eq(AuditManageInfo::getSourceId, dto.getSourceId());
        infoQuery.ne(AuditManageInfo::getStatus, 2);
        AssertUtil.isTrue(infoService.count(infoQuery) == 0, "该项目已在审核中");
        // 查审批初始节点
        AuditManageProcess auditProcess = processService.firstProcess(dto.getAuditName());
        AssertUtil.notNull(auditProcess, "审核流程不存在");
        // 存log
        AuditManageLog auditLog = new AuditManageLog();
        auditLog.setProcessId(auditProcess.getId());
        auditLog.setSourceId(dto.getSourceId());
        auditLog.setStatus(0);
        auditLog.setText(dto.getText());
        logService.save(auditLog);
        // 存info
        AuditManageInfo info = new AuditManageInfo();
        info.setProcessId(auditProcess.getId());
        info.setSourceId(dto.getSourceId());
        info.setLogId(auditLog.getId());
        info.setStatus(0);
        info.setCreateTime(new Date());
        info.setUpdateTime(new Date());
        infoService.insertOrUpdate(info);
        return JsonResult.success();
    }

    /**
     * 审核
     *
     * @param dto
     * @return
     */
    @PostMapping("audit")
    public JsonResult<Void> audit(@RequestBody @Valid AuditDto dto) {
        // 查下一个审核节点
        AuditManageProcess auditProcess = processService.getBySourceId(dto.getSourceId());
        AssertUtil.notNull(auditProcess, "审核节点不存在");
        AssertUtil.isTrue(auditProcess.getUserIds().contains(SecurityUtils.getUserId()), "没有审核权限");
        // 更新审核记录
        AuditManageLog updated = new AuditManageLog();
        updated.setStatus(dto.getStatus());
        updated.setText(dto.getText());
        updated.setAuditUser(SecurityUtils.getUserId());
        updated.setUpdateTime(updated.getUpdateTime());
        updated.setUpdateUser(SecurityUtils.getUserId());
        logService.updateByProcessIdAndSourceId(updated, auditProcess.getId(), dto.getSourceId());
        // 添加下级审核，更新审核状态
        AuditManageInfo info = new AuditManageInfo();
        info.setSourceId(dto.getSourceId());
        info.setUpdateTime(new Date());
        if (dto.getStatus() == 1) {
            // 审核通过
            AuditManageProcess nextProcess = processService.nextProcess(auditProcess);
            // 有下一个节点，继续审核
            if (nextProcess != null) {
                AuditManageLog auditLog = new AuditManageLog();
                auditLog.setProcessId(nextProcess.getId());
                auditLog.setSourceId(dto.getSourceId());
                auditLog.setStatus(0);
                logService.save(auditLog);
                info.setLogId(auditLog.getId());
                info.setProcessId(nextProcess.getId());
                info.setStatus(0);
            } else {
                info.setStatus(1);
            }
        } else if (dto.getStatus() == 2) {
            info.setStatus(2);
        }
        infoService.updateBySourceId(info, dto.getSourceId());
        return JsonResult.success();
    }
}
