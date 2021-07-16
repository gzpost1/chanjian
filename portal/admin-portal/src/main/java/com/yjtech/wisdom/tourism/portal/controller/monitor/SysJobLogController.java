package com.yjtech.wisdom.tourism.portal.controller.monitor;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.annotation.Log;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.enums.BusinessType;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseController;
import com.yjtech.wisdom.tourism.infrastructure.poi.ExcelUtil;
import com.yjtech.wisdom.tourism.scheduler.domain.SysJobLog;
import com.yjtech.wisdom.tourism.scheduler.service.SysJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 调度日志操作处理
 *
 * @author liuhong
 */
@RestController
@RequestMapping("/monitor/jobLog")
public class SysJobLogController extends BaseController {
  @Autowired private SysJobLogService jobLogService;

  /** 查询定时任务调度日志列表 */
  @PreAuthorize("@ss.hasPermi('monitor:job:list')")
  @GetMapping("/list")
  public JsonResult<IPage<SysJobLog>> list(SysJobLog sysJobLog) {
    startPage();
    IPage<SysJobLog> list = jobLogService.selectJobLogList(sysJobLog);
    return JsonResult.success(list);
  }

  /** 导出定时任务调度日志列表 */
  @PreAuthorize("@ss.hasPermi('monitor:job:export')")
  @Log(title = "任务调度日志", businessType = BusinessType.EXPORT)
  @GetMapping("/export")
  public JsonResult export(SysJobLog sysJobLog) {
    IPage<SysJobLog> list = jobLogService.selectJobLogList(sysJobLog);
    ExcelUtil<SysJobLog> util = new ExcelUtil<SysJobLog>(SysJobLog.class);
    return util.exportExcel(list.getRecords(), "调度日志");
  }

  /** 根据调度编号获取详细信息 */
  @PreAuthorize("@ss.hasPermi('monitor:job:query')")
  @GetMapping(value = "/{configId}")
  public JsonResult getInfo(@PathVariable Long jobLogId) {
    return JsonResult.success(jobLogService.selectJobLogById(jobLogId));
  }

  /** 删除定时任务调度日志 */
  @PreAuthorize("@ss.hasPermi('monitor:job:remove')")
  @Log(title = "定时任务调度日志", businessType = BusinessType.DELETE)
  @DeleteMapping("/{jobLogIds}")
  public JsonResult remove(@PathVariable Long[] jobLogIds) {
    return JsonResult.success(jobLogService.deleteJobLogByIds(jobLogIds));
  }

  /** 清空定时任务调度日志 */
  @PreAuthorize("@ss.hasPermi('monitor:job:remove')")
  @Log(title = "调度日志", businessType = BusinessType.CLEAN)
  @DeleteMapping("/clean")
  public JsonResult clean() {
    jobLogService.cleanJobLog();
    return JsonResult.success();
  }
}
