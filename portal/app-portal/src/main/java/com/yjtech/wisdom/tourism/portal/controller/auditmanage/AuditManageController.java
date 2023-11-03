package com.yjtech.wisdom.tourism.portal.controller.auditmanage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.project.service.TbProjectInfoService;
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
@RequestMapping("/screen/auditmanage")
public class AuditManageController {
    @Autowired
    private AuditManageProcessService processService;
    @Autowired
    private AuditManageLogService logService;
    @Autowired
    private AuditManageInfoService infoService;
    @Autowired
    private TbProjectInfoService projectInfoService;

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
        // 存提交log
        AuditManageLog commitLog = new AuditManageLog();
        commitLog.setProcessId(-1L);
        commitLog.setSourceId(dto.getSourceId());
        commitLog.setType(0);
        commitLog.setStatus(1);
        logService.save(commitLog);
        // 存log
        AuditManageLog auditLog = new AuditManageLog();
        auditLog.setProcessId(auditProcess.getId());
        auditLog.setSourceId(dto.getSourceId());
        auditLog.setType(1);
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
        projectInfoService.updateStatusById(EntityConstants.NO, dto.getSourceId());
        return JsonResult.success();
    }
}
