package com.yjtech.wisdom.tourism.portal.controller.system;

import com.yjtech.wisdom.tourism.common.annotation.Log;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.constant.UserConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.enums.BusinessType;
import com.yjtech.wisdom.tourism.common.utils.ServletUtils;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.framework.web.service.TokenService;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseController;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysMenu;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.LoginUser;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单信息
 *
 * @author liuhong
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {
  @Autowired private SysMenuService menuService;

  @Autowired private TokenService tokenService;

  /** 获取菜单列表 */
  @PreAuthorize("@ss.hasPermi('system:menu:list')")
  @GetMapping("/list")
  public JsonResult list(SysMenu menu) {
    LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
    Long userId = loginUser.getUser().getUserId();
    List<SysMenu> menus = menuService.selectMenuList(menu, userId);
    return JsonResult.success(menus);
  }

  /** 根据菜单编号获取详细信息 */
  @PreAuthorize("@ss.hasPermi('system:menu:query')")
  @GetMapping(value = "/{menuId}")
  public JsonResult getInfo(@PathVariable Long menuId) {
    return JsonResult.success(menuService.selectMenuById(menuId));
  }

  /** 获取菜单下拉树列表 */
  @GetMapping("/treeselect")
  public JsonResult treeselect(SysMenu menu) {
    LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
    Long userId = loginUser.getUser().getUserId();
    List<SysMenu> menus = menuService.selectMenuList(menu, userId);
    return JsonResult.success(menuService.buildMenuTreeSelect(menus));
  }

  /** 加载对应角色菜单列表树 */
  @GetMapping(value = "/roleMenuTreeselect/{roleId}")
  public JsonResult roleMenuTreeselect(@PathVariable("roleId") Long roleId) {
    LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
    List<SysMenu> menus = menuService.selectMenuList(loginUser.getUser().getUserId());
    Map<String, Object> result = new HashMap<>();
    result.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
    result.put("menus", menuService.buildMenuTreeSelect(menus));
    return JsonResult.success(result);
  }

  /** 新增菜单 */
  @PreAuthorize("@ss.hasPermi('system:menu:add')")
  @Log(title = "菜单管理", businessType = BusinessType.INSERT)
  @PostMapping
  public JsonResult add(@Validated @RequestBody SysMenu menu) {
    if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
      return JsonResult.error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
    } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame())
        && !StringUtils.startsWithAny(menu.getPath(), Constants.HTTP, Constants.HTTPS)) {
      return JsonResult.error("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
    }
    menu.setCreateUser(SecurityUtils.getUserId());
    return success(menuService.insertMenu(menu));
  }

  /** 修改菜单 */
  @PreAuthorize("@ss.hasPermi('system:menu:edit')")
  @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
  @PutMapping
  public JsonResult edit(@Validated @RequestBody SysMenu menu) {
    if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
      return JsonResult.error("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
    } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame())
        && !StringUtils.startsWithAny(menu.getPath(), Constants.HTTP, Constants.HTTPS)) {
      return JsonResult.error("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
    } else if (menu.getMenuId().equals(menu.getParentId())) {
      return JsonResult.error("新增菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
    }
    menu.setUpdateUser(SecurityUtils.getUserId());
    return success(menuService.updateMenu(menu));
  }

  /** 删除菜单 */
  @PreAuthorize("@ss.hasPermi('system:menu:remove')")
  @Log(title = "菜单管理", businessType = BusinessType.DELETE)
  @DeleteMapping("/{menuId}")
  public JsonResult remove(@PathVariable("menuId") Long menuId) {
    if (menuService.hasChildByMenuId(menuId)) {
      return JsonResult.error("存在子菜单,不允许删除");
    }
    if (menuService.checkMenuExistRole(menuId)) {
      return JsonResult.error("菜单已分配,不允许删除");
    }
    return success(menuService.deleteMenuById(menuId));
  }
}
