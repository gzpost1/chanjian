package com.yjtech.wisdom.tourism.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.infrastructure.core.page.TableSupport;
import com.yjtech.wisdom.tourism.system.domain.SysLogininfor;
import com.yjtech.wisdom.tourism.system.mapper.SysLogininforMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统访问日志情况信息 服务层处理
 *
 * @author liuhong
 */
@Service
public class SysLogininforService {

  @Autowired private SysLogininforMapper logininforMapper;

  /**
   * 新增系统登录日志
   *
   * @param logininfor 访问日志对象
   */
  public void insertLogininfor(SysLogininfor logininfor) {
    logininforMapper.insertLogininfor(logininfor);
  }

  /**
   * 查询系统登录日志集合
   *
   * @param logininfor 访问日志对象
   * @return 登录记录集合
   */
  public IPage<SysLogininfor> selectLogininforList(SysLogininfor logininfor) {
    IPage<SysLogininfor> pageDomain = TableSupport.buildIPageRequest();
    return logininforMapper.selectLogininforList(pageDomain, logininfor);
  }

  /**
   * 批量删除系统登录日志
   *
   * @param infoIds 需要删除的登录日志ID
   * @return
   */
  public int deleteLogininforByIds(Long[] infoIds) {
    return logininforMapper.deleteLogininforByIds(infoIds);
  }

  /** 清空系统登录日志 */
  public void cleanLogininfor() {
    logininforMapper.cleanLogininfor();
  }
}
