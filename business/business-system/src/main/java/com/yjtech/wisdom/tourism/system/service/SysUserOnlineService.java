package com.yjtech.wisdom.tourism.system.service;

import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.LoginUser;
import com.yjtech.wisdom.tourism.system.domain.SysUserOnline;
import org.springframework.stereotype.Service;

/**
 * 在线用户 服务层处理
 *
 * @author liuhong
 */
@Service
public class SysUserOnlineService {
  /**
   * 通过登录地址查询信息
   *
   * @param ipaddr 登录地址
   * @param user 用户信息
   * @return 在线用户信息
   */
  public SysUserOnline selectOnlineByIpaddr(String ipaddr, LoginUser user) {
    if (StringUtils.equals(ipaddr, user.getIpaddr())) {
      return loginUserToUserOnline(user);
    }
    return null;
  }

  /**
   * 通过用户名称查询信息
   *
   * @param userName 用户名称
   * @param user 用户信息
   * @return 在线用户信息
   */
  public SysUserOnline selectOnlineByUserName(String userName, LoginUser user) {
    if (StringUtils.equals(userName, user.getUsername())) {
      return loginUserToUserOnline(user);
    }
    return null;
  }

  /**
   * 通过登录地址/用户名称查询信息
   *
   * @param ipaddr 登录地址
   * @param userName 用户名称
   * @param user 用户信息
   * @return 在线用户信息
   */
  public SysUserOnline selectOnlineByInfo(String ipaddr, String userName, LoginUser user) {
    if (StringUtils.equals(ipaddr, user.getIpaddr())
        && StringUtils.equals(userName, user.getUsername())) {
      return loginUserToUserOnline(user);
    }
    return null;
  }

  /**
   * 设置在线用户信息
   *
   * @param user 用户信息
   * @return 在线用户
   */
  public SysUserOnline loginUserToUserOnline(LoginUser user) {
    if (StringUtils.isNull(user) || StringUtils.isNull(user.getUser())) {
      return null;
    }
    SysUserOnline sysUserOnline = new SysUserOnline();
    sysUserOnline.setTokenId(user.getToken());
    sysUserOnline.setUserName(user.getUsername());
    sysUserOnline.setIpaddr(user.getIpaddr());
    sysUserOnline.setLoginLocation(user.getLoginLocation());
    sysUserOnline.setBrowser(user.getBrowser());
    sysUserOnline.setOs(user.getOs());
    sysUserOnline.setLoginTime(user.getLoginTime());
    if (StringUtils.isNotNull(user.getUser().getDept())) {
      sysUserOnline.setDeptName(user.getUser().getDept().getDeptName());
    }
    return sysUserOnline;
  }
}
