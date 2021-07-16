package com.yjtech.wisdom.tourism.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.system.domain.SysLogininfor;
import org.apache.ibatis.annotations.Param;

/**
 * 系统访问日志情况信息 数据层
 *
 * @author liuhong
 */
public interface SysLogininforMapper {
  /**
   * 新增系统登录日志
   *
   * @param logininfor 访问日志对象
   */
  public void insertLogininfor(SysLogininfor logininfor);

  /**
   * 查询系统登录日志集合
   *
   * @param logininfor 访问日志对象
   * @return 登录记录集合
   */
  public IPage<SysLogininfor> selectLogininforList(
      IPage<SysLogininfor> pageDomain, @Param("args") SysLogininfor logininfor);

  /**
   * 批量删除系统登录日志
   *
   * @param infoIds 需要删除的登录日志ID
   * @return 结果
   */
  public int deleteLogininforByIds(Long[] infoIds);

  /**
   * 清空系统登录日志
   *
   * @return 结果
   */
  public int cleanLogininfor();
}
