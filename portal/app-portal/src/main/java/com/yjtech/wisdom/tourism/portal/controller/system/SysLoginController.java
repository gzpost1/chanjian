package com.yjtech.wisdom.tourism.portal.controller.system;

import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.ServletUtils;
import com.yjtech.wisdom.tourism.framework.web.service.SysLoginService;
import com.yjtech.wisdom.tourism.framework.web.service.SysPermissionService;
import com.yjtech.wisdom.tourism.framework.web.service.TokenService;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.LoginBody;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.LoginUser;
import com.yjtech.wisdom.tourism.system.service.SysMenuService;
import com.yjtech.wisdom.tourism.systemconfig.chart.service.SystemconfigChartsService;
import com.yjtech.wisdom.tourism.systemconfig.menu.service.SystemconfigMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 登录验证
 *
 * @author liuhong
 */
@RestController
public class SysLoginController {
  @Autowired private SysLoginService loginService;

  @Autowired private SysMenuService menuService;

  @Autowired private SysPermissionService permissionService;

  @Autowired private TokenService tokenService;

  @Autowired
  private SystemconfigMenuService systemconfigMenuService;

  /**
   * 登录方法
   *
   * @param loginBody 登录信息
   * @return 结果
   */
  @PostMapping("/login")
  public JsonResult login(@RequestBody LoginBody loginBody) throws Exception{
    // 生成令牌
    String token =
        loginService.login(
            loginBody.getUsername(),
            loginBody.getPassword(),
            loginBody.getCode(),
            loginBody.getUuid(),
            loginBody.getAppUser());
    return JsonResult.success(token);
  }

  /**
   * 获取用户信息
   *
   * @return 用户信息
   */
  @GetMapping("getInfo")
  public JsonResult getInfo() {
    LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
    SysUser user = loginUser.getUser();
    // 角色集合
    Set<String> roles = permissionService.getRolePermission(user);
    // 权限集合
    Set<String> permissions = permissionService.getMenuPermission(user);
    Map<String, Object> result = new HashMap<>();
    result.put("user", user);
    result.put("roles", roles);
    result.put("permissions", permissions);
    return JsonResult.success(result);
  }

  /**
   * 获取路由信息
   *
   * @return 路由信息
   */
  @GetMapping("getRouters")
  public JsonResult getRouters() {
    return JsonResult.success(systemconfigMenuService.getDatavMenu());
  }

  /**
   * 获取点位导航选中/未选中图标，传入图表id
   * @param idParam  图表id
   * @return         点位图表信息
   */
  @PostMapping("getIcons")
  public JsonResult getIcons(@RequestBody @Valid IdParam idParam) {
    return JsonResult.success(systemconfigMenuService.getIcons(idParam.getId()));
  }

}
