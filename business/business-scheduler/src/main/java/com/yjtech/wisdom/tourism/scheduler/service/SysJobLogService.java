package com.yjtech.wisdom.tourism.scheduler.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.yjtech.wisdom.tourism.infrastructure.core.page.TableSupport;
import com.yjtech.wisdom.tourism.scheduler.domain.SysJobLog;
import com.yjtech.wisdom.tourism.scheduler.mapper.SysJobLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 定时任务调度日志信息 服务层
 *
 * @author liuhong
 */
@Service
public class SysJobLogService {
  @Autowired private SysJobLogMapper jobLogMapper;

  /**
   * 获取quartz调度器日志的计划任务
   *
   * @param jobLog 调度日志信息
   * @return 调度任务日志集合
   */
  public IPage<SysJobLog> selectJobLogList(SysJobLog jobLog) {
    IPage<SysJobLog> pageDomain = TableSupport.buildIPageRequest();
    return jobLogMapper.selectJobLogList(pageDomain, jobLog);
  }

  /**
   * 通过调度任务日志ID查询调度信息
   *
   * @param jobLogId 调度任务日志ID
   * @return 调度任务日志对象信息
   */
  public SysJobLog selectJobLogById(Long jobLogId) {
    return jobLogMapper.selectJobLogById(jobLogId);
  }

  /**
   * 新增任务日志
   *
   * @param jobLog 调度日志信息
   */
  public void addJobLog(SysJobLog jobLog) {
    jobLog.setJobLogId(IdWorker.getId());
    jobLogMapper.insertJobLog(jobLog);
  }

  /**
   * 批量删除调度日志信息
   *
   * @param logIds 需要删除的数据ID
   * @return 结果
   */
  public int deleteJobLogByIds(Long[] logIds) {
    return jobLogMapper.deleteJobLogByIds(logIds);
  }

  /**
   * 删除任务日志
   *
   * @param jobId 调度日志ID
   */
  public int deleteJobLogById(Long jobId) {
    return jobLogMapper.deleteJobLogById(jobId);
  }

  /** 清空任务日志 */
  public void cleanJobLog() {
    jobLogMapper.cleanJobLog();
  }
}
