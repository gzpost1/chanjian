package com.yjtech.wisdom.tourism.portal.controller.auditmanage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
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
     * @param dto
     * @return
     */
    @PostMapping("commit")
    public JsonResult<Void> commit(@RequestBody @Valid AuditDto dto) {
        // 如果source审核失败或者不存在审核，则可以提交
        LambdaQueryWrapper<AuditManageLog> logQuery = Wrappers.lambdaQuery();
        logQuery.eq(AuditManageLog::getSourceId, dto.getSourceId());
        logQuery.ne(AuditManageLog::getStatus, 2);
        AssertUtil.isTrue(logService.count(logQuery) == 0, "");
        // 查审批初始节点
        AuditManageProcess auditProcess = processService.firstProcess(dto.getAuditName());
        // 存log
        AuditManageLog auditLog = new AuditManageLog();
        auditLog.setAction(1);
        auditLog.setStatus(dto.getStatus());
        auditLog.setSourceId(dto.getSourceId());
        auditLog.setText(dto.getText());
        auditLog.setProcessId(auditProcess.getId());
        logService.save(auditLog);
        // 存info
        AuditManageInfo info = new AuditManageInfo();
        info.setProcessId(auditProcess.getId());
        info.setStatus(0);
        info.setSourceId(dto.getSourceId());
        infoService.save(info);
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
        AuditManageProcess auditProcess = processService.nextProcess(dto.getSourceId());
        AssertUtil.notNull(auditProcess, "不存在下一个审核节点");
        // 保存审核记录
        AuditManageLog auditLog = new AuditManageLog();
        auditLog.setAction(1);
        auditLog.setStatus(dto.getStatus());
        auditLog.setSourceId(dto.getSourceId());
        auditLog.setText(dto.getText());
        auditLog.setProcessId(auditProcess.getId());
        logService.save(auditLog);
        // 保存审核结果
        AuditManageProcess nextProcess = processService.nextProcess(dto.getSourceId());
        AuditManageInfo info = new AuditManageInfo();
        info.setProcessId(auditProcess.getId());
        info.setSourceId(dto.getSourceId());
        if (nextProcess != null) {
            info.setNextAuditUserIds(nextProcess.getUserIds());
        }
        if (nextProcess == null && dto.getStatus() == 1) {
            info.setStatus(1);
        } else if (dto.getStatus() == 2) {
            info.setStatus(2);
        } else {
            info.setStatus(0);
        }
        infoService.save(info);
        return JsonResult.success();
    }
}
