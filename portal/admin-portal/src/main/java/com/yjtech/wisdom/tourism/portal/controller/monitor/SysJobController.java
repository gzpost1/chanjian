package com.yjtech.wisdom.tourism.portal.controller.monitor;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.annotation.Log;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.enums.BusinessType;
import com.yjtech.wisdom.tourism.common.exception.job.TaskException;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseController;
import com.yjtech.wisdom.tourism.infrastructure.poi.ExcelUtil;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.scheduler.domain.SysJob;
import com.yjtech.wisdom.tourism.scheduler.service.SysJobService;
import com.yjtech.wisdom.tourism.scheduler.util.CronUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 调度任务信息操作处理
 *
 * @author liuhong
 */
@RestController
@RequestMapping("/monitor/job")
public class SysJobController extends BaseController {
  @Autowired private SysJobService jobService;

  /** 查询定时任务列表 */
  @PreAuthorize("@ss.hasPermi('monitor:job:list')")
  @GetMapping("/list")
  public JsonResult<IPage<SysJob>> list(SysJob sysJob) {
    startPage();
    IPage<SysJob> list = jobService.selectJobList(sysJob);
    return JsonResult.success(list);
  }

  /** 导出定时任务列表 */
  @PreAuthorize("@ss.hasPermi('monitor:job:export')")
  @Log(title = "定时任务", businessType = BusinessType.EXPORT)
  @GetMapping("/export")
  public JsonResult export(SysJob sysJob) {
    IPage<SysJob> list = jobService.selectJobList(sysJob);
    ExcelUtil<SysJob> util = new ExcelUtil<SysJob>(SysJob.class);
    return util.exportExcel(list.getRecords(), "定时任务");
  }

  /** 获取定时任务详细信息 */
  @PreAuthorize("@ss.hasPermi('monitor:job:query')")
  @GetMapping(value = "/{jobId}")
  public JsonResult getInfo(@PathVariable("jobId") Long jobId) {
    return JsonResult.success(jobService.selectJobById(jobId));
  }

  /** 新增定时任务 */
  @PreAuthorize("@ss.hasPermi('monitor:job:add')")
  @Log(title = "定时任务", businessType = BusinessType.INSERT)
  @PostMapping
  public JsonResult add(@RequestBody SysJob sysJob) throws SchedulerException, TaskException {
    if (!CronUtils.isValid(sysJob.getCronExpression())) {
      return JsonResult.error("cron表达式不正确");
    }
    sysJob.setCreateUser(SecurityUtils.getUserId());
    return JsonResult.success(jobService.insertJob(sysJob));
  }

  /** 修改定时任务 */
  @PreAuthorize("@ss.hasPermi('monitor:job:edit')")
  @Log(title = "定时任务", businessType = BusinessType.UPDATE)
  @PutMapping
  public JsonResult edit(@RequestBody SysJob sysJob) throws SchedulerException, TaskException {
    if (!CronUtils.isValid(sysJob.getCronExpression())) {
      return JsonResult.error("cron表达式不正确");
    }
    sysJob.setUpdateUser(SecurityUtils.getUserId());
    return JsonResult.success(jobService.updateJob(sysJob));
  }

  /** 定时任务状态修改 */
  @PreAuthorize("@ss.hasPermi('monitor:job:changeStatus')")
  @Log(title = "定时任务", businessType = BusinessType.UPDATE)
  @PutMapping("/changeStatus")
  public JsonResult changeStatus(@RequestBody SysJob job) throws SchedulerException {
    SysJob newJob = jobService.selectJobById(job.getJobId());
    newJob.setStatus(job.getStatus());
    return JsonResult.success(jobService.changeStatus(newJob));
  }

  /** 定时任务立即执行一次 */
  @PreAuthorize("@ss.hasPermi('monitor:job:changeStatus')")
  @Log(title = "定时任务", businessType = BusinessType.UPDATE)
  @PutMapping("/run")
  public JsonResult run(@RequestBody SysJob job) throws SchedulerException {
    jobService.run(job);
    return JsonResult.success();
  }

  /** 删除定时任务 */
  @PreAuthorize("@ss.hasPermi('monitor:job:remove')")
  @Log(title = "定时任务", businessType = BusinessType.DELETE)
  @DeleteMapping("/{jobIds}")
  public JsonResult remove(@PathVariable Long[] jobIds) throws SchedulerException, TaskException {
    jobService.deleteJobByIds(jobIds);
    return JsonResult.success();
  }
}
