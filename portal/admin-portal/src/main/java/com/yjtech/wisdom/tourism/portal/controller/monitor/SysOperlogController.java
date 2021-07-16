package com.yjtech.wisdom.tourism.portal.controller.monitor;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.annotation.Log;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.enums.BusinessType;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseController;
import com.yjtech.wisdom.tourism.infrastructure.poi.ExcelUtil;
import com.yjtech.wisdom.tourism.system.domain.SysOperLog;
import com.yjtech.wisdom.tourism.system.service.SysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 操作日志记录
 *
 * @author liuhong
 */
@RestController
@RequestMapping("/monitor/operlog")
public class SysOperlogController extends BaseController {
  @Autowired private SysOperLogService operLogService;

  @PreAuthorize("@ss.hasPermi('monitor:operlog:list')")
  @GetMapping("/list")
  public JsonResult<IPage<SysOperLog>> list(SysOperLog operLog) {
    startPage();
    IPage<SysOperLog> list = operLogService.selectOperLogList(operLog);
    return JsonResult.success(list);
  }

  @Log(title = "操作日志", businessType = BusinessType.EXPORT)
  @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
  @GetMapping("/export")
  public JsonResult export(SysOperLog operLog) {
    IPage<SysOperLog> list = operLogService.selectOperLogList(operLog);
    ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
    return util.exportExcel(list.getRecords(), "操作日志");
  }

  @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
  @DeleteMapping("/{operIds}")
  public JsonResult remove(@PathVariable Long[] operIds) {
    return JsonResult.success(operLogService.deleteOperLogByIds(operIds));
  }

  @Log(title = "操作日志", businessType = BusinessType.CLEAN)
  @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
  @DeleteMapping("/clean")
  public JsonResult clean() {
    operLogService.cleanOperLog();
    return JsonResult.success();
  }
}
