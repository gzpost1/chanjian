package com.yjtech.wisdom.tourism.portal.controller.system;

import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.ServletUtils;
import com.yjtech.wisdom.tourism.framework.web.service.SysLoginService;
import com.yjtech.wisdom.tourism.framework.web.service.SysPermissionService;
import com.yjtech.wisdom.tourism.framework.web.service.TokenService;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.LoginBody;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.LoginUser;
import com.yjtech.wisdom.tourism.system.dto.SysUserAllInfoDTO;
import com.yjtech.wisdom.tourism.system.service.SysMenuService;
import com.yjtech.wisdom.tourism.systemconfig.architecture.dto.MenuTreeNode;
import com.yjtech.wisdom.tourism.systemconfig.architecture.service.TbSystemconfigArchitectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.List;

/**
 * 登录验证
 *
 * @author liuhong
 */
@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private SysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TbSystemconfigArchitectureService service;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public JsonResult login(@RequestBody LoginBody loginBody) throws Exception {
        // 生成令牌
        String token =
                loginService.login(
                        loginBody.getUsername(),
                        loginBody.getPassword(),
                        loginBody.getCode(),
                        loginBody.getUuid(),
                        loginBody.getAppUser(),
                        loginBody.getPushToken());
        return JsonResult.success(token);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public JsonResult<SysUserAllInfoDTO> getInfo() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);

        return JsonResult.success(new SysUserAllInfoDTO(user, roles, permissions));
    }

    /**
     * 获取路由信息
     * @todo  指标库信息未录入
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public JsonResult<List<MenuTreeNode>> getRouters() {
        return JsonResult.success(service.getDatavMenu());
    }


    /**
     * 登出
     *
     * @return 结果
     */
    @GetMapping("/logout")
    public JsonResult logout() {
        //获取当前用户信息
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Assert.notNull(loginUser, ErrorCode.NO_PERMISSION.getMessage());
        //登出
        tokenService.delLoginUser(loginUser.getToken());
        return JsonResult.success();
    }

}
