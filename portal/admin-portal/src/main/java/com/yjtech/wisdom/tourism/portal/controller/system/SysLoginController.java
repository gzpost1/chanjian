package com.yjtech.wisdom.tourism.portal.controller.system;

import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.ServletUtils;
import com.yjtech.wisdom.tourism.framework.web.service.SysLoginService;
import com.yjtech.wisdom.tourism.framework.web.service.SysPermissionService;
import com.yjtech.wisdom.tourism.framework.web.service.TokenService;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysMenu;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.LoginBody;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.LoginUser;
import com.yjtech.wisdom.tourism.system.domain.vo.RouterVo;
import com.yjtech.wisdom.tourism.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
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
                false);
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
    LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
    // 用户信息
    SysUser user = loginUser.getUser();
    List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId());
    List<RouterVo>  ss = menuService.buildMenus(menus);
    System.err.println(ss);
    JsonResult result = JsonResult.success(ss);
    return result;
  }
}
