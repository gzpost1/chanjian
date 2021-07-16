package com.yjtech.wisdom.tourism.portal.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.annotation.Log;
import com.yjtech.wisdom.tourism.common.constant.UserConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.enums.BusinessType;
import com.yjtech.wisdom.tourism.common.utils.ServletUtils;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.framework.web.service.SysPermissionService;
import com.yjtech.wisdom.tourism.framework.web.service.TokenService;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseController;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysRole;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.LoginUser;
import com.yjtech.wisdom.tourism.infrastructure.poi.ExcelUtil;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.system.service.SysRoleService;
import com.yjtech.wisdom.tourism.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 角色信息
 *
 * @author liuhong
 */
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {
  @Autowired private SysRoleService roleService;

  @Autowired private TokenService tokenService;

  @Autowired private SysPermissionService permissionService;

  @Autowired private SysUserService userService;

  @PreAuthorize("@ss.hasPermi('system:role:list')")
  @GetMapping("/list")
  public JsonResult<IPage<SysRole>> list(SysRole role) {
    startPage();
    IPage<SysRole> list = roleService.selectRoleList(role);
    return JsonResult.success(list);
  }

  @Log(title = "角色管理", businessType = BusinessType.EXPORT)
  @PreAuthorize("@ss.hasPermi('system:role:export')")
  @GetMapping("/export")
  public JsonResult export(SysRole role) {
    IPage<SysRole> list = roleService.selectRoleList(role);
    ExcelUtil<SysRole> util = new ExcelUtil<SysRole>(SysRole.class);
    return util.exportExcel(list.getRecords(), "角色数据");
  }

  /** 根据角色编号获取详细信息 */
  @PreAuthorize("@ss.hasPermi('system:role:query')")
  @GetMapping(value = "/{roleId}")
  public JsonResult getInfo(@PathVariable Long roleId) {
    return JsonResult.success(roleService.selectRoleById(roleId));
  }

  /** 新增角色 */
  @PreAuthorize("@ss.hasPermi('system:role:add')")
  @Log(title = "角色管理", businessType = BusinessType.INSERT)
  @PostMapping
  public JsonResult add(@Validated @RequestBody SysRole role) {
    // 使用默认的角色字符串
    role.setRoleKey("default");
    if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
      return JsonResult.error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
//    } else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
//      return JsonResult.error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
    }
    role.setCreateUser(SecurityUtils.getUserId());
    roleService.insertRole(role);
    return success(role);
  }

  /** 修改保存角色 */
  @PreAuthorize("@ss.hasPermi('system:role:edit')")
  @Log(title = "角色管理", businessType = BusinessType.UPDATE)
  @PutMapping
  public JsonResult edit(@Validated @RequestBody SysRole role) {
    // 使用默认的角色字符串
    role.setRoleKey("default");
    roleService.checkRoleAllowed(role);
    if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
      return JsonResult.error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
//    } else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
//      return JsonResult.error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
    }
    role.setUpdateUser(SecurityUtils.getUserId());

    if (roleService.updateRole(role) > 0) {
      // 更新缓存用户权限
      LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
      if (StringUtils.isNotNull(loginUser.getUser()) && !loginUser.getUser().isAdmin()) {
        loginUser.setPermissions(permissionService.getMenuPermission(loginUser.getUser()));
        loginUser.setUser(userService.selectUserByUserName(loginUser.getUser().getUserName()));
        tokenService.setLoginUser(loginUser);
      }
      return JsonResult.success(role);
    }
    return JsonResult.error("修改角色'" + role.getRoleName() + "'失败，请联系管理员");
  }

  /** 修改保存数据权限 */
  @PreAuthorize("@ss.hasPermi('system:role:edit')")
  @Log(title = "角色管理", businessType = BusinessType.UPDATE)
  @PutMapping("/dataScope")
  public JsonResult dataScope(@RequestBody SysRole role) {
    roleService.checkRoleAllowed(role);
    return success(roleService.authDataScope(role));
  }

  /** 状态修改 */
  @PreAuthorize("@ss.hasPermi('system:role:edit')")
  @Log(title = "角色管理", businessType = BusinessType.UPDATE)
  @PutMapping("/changeStatus")
  public JsonResult changeStatus(@RequestBody SysRole role) {
    roleService.checkRoleAllowed(role);
    role.setUpdateUser(SecurityUtils.getUserId());
    return success(roleService.updateRoleStatus(role));
  }

  /** 删除角色 */
  @PreAuthorize("@ss.hasPermi('system:role:remove')")
  @Log(title = "角色管理", businessType = BusinessType.DELETE)
  @DeleteMapping("/{roleIds}")
  public JsonResult remove(@PathVariable Long[] roleIds) {
    return success(roleService.deleteRoleByIds(roleIds));
  }

  /** 获取角色选择框列表 */
  @PreAuthorize("@ss.hasPermi('system:role:query')")
  @GetMapping("/optionselect")
  public JsonResult optionselect() {
    return JsonResult.success(roleService.selectRoleAll());
  }
}
