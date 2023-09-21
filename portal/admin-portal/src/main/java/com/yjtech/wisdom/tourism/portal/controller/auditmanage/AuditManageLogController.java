package com.yjtech.wisdom.tourism.portal.controller.auditmanage;

import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.resource.auditmanage.entity.AuditManageLog;
import com.yjtech.wisdom.tourism.resource.auditmanage.service.AuditManageLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 【后台】审核日志
 *
 * @author songjun
 * @since 2023/9/8
 */
@Slf4j
@RestController
@RequestMapping("/auditmanage/log")
public class AuditManageLogController {
    @Autowired
    private AuditManageLogService logService;

    /**
     * 列表
     *
     * @param idParam
     * @return
     */
    @PostMapping("list")
    public JsonResult<List<AuditManageLog>> list(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(logService.auditLogList(idParam.getId()));
    }
}
