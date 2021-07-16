package com.yjtech.wisdom.tourism.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import com.yjtech.wisdom.tourism.infrastructure.core.page.TableSupport;
import com.yjtech.wisdom.tourism.system.domain.SysOperLog;
import com.yjtech.wisdom.tourism.system.mapper.SysOperLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作日志 服务层处理
 *
 * @author liuhong
 */
@Service
public class SysOperLogService {
  @Autowired private SysOperLogMapper operLogMapper;

  /**
   * 新增操作日志
   *
   * @param operLog 操作日志对象
   */
  public void insertOperlog(SysOperLog operLog) {
    operLogMapper.insertOperlog(operLog);
  }

  /**
   * 查询系统操作日志集合
   *
   * @param operLog 操作日志对象
   * @return 操作日志集合
   */
  public IPage<SysOperLog> selectOperLogList(SysOperLog operLog) {
    IPage<SysOperLog> pageDomain = TableSupport.buildIPageRequest();
    return operLogMapper.selectOperLogList(pageDomain, operLog);
  }

  /**
   * 批量删除系统操作日志
   *
   * @param operIds 需要删除的操作日志ID
   * @return 结果
   */
  public int deleteOperLogByIds(Long[] operIds) {
    return operLogMapper.deleteOperLogByIds(operIds);
  }

  /**
   * 查询操作日志详细
   *
   * @param operId 操作ID
   * @return 操作日志对象
   */
  public SysOperLog selectOperLogById(Long operId) {
    return operLogMapper.selectOperLogById(operId);
  }

  /** 清空操作日志 */
  public void cleanOperLog() {
    operLogMapper.cleanOperLog();
  }
}
