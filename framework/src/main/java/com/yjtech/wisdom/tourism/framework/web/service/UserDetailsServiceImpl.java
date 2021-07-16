package com.yjtech.wisdom.tourism.framework.web.service;

import com.yjtech.wisdom.tourism.common.enums.UserStatus;
import com.yjtech.wisdom.tourism.common.exception.BaseException;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.LoginUser;
import com.yjtech.wisdom.tourism.system.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户验证处理
 *
 * @author liuhong
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

  @Autowired private SysUserService userService;

  @Autowired private SysPermissionService permissionService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    SysUser user = userService.selectUserByUserName(username);
    if (StringUtils.isNull(user)) {
      log.info("登录用户：{} 不存在.", username);
      throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
    } else if (1 == user.getDeleted()) {
      log.info("登录用户：{} 已被删除.", username);
      throw new BaseException("对不起，您的账号：" + username + " 已被删除");
    } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
      log.info("登录用户：{} 已被停用.", username);
      throw new BaseException("对不起，您的账号：" + username + " 已停用");
    }

    return createLoginUser(user);
  }

  public UserDetails createLoginUser(SysUser user) {
    return new LoginUser(user, permissionService.getMenuPermission(user));
  }
}
