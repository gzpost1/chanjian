package com.yjtech.wisdom.tourism.portal.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.annotation.Log;
import com.yjtech.wisdom.tourism.common.constant.UserConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.enums.BusinessType;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.ServletUtils;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.common.utils.password.CheckPwd;
import com.yjtech.wisdom.tourism.framework.web.service.TokenService;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseController;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysRole;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.LoginUser;
import com.yjtech.wisdom.tourism.infrastructure.poi.ExcelUtil;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.system.service.SysPostService;
import com.yjtech.wisdom.tourism.system.service.SysRoleService;
import com.yjtech.wisdom.tourism.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户信息
 *
 * @author liuhong
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
  @Autowired private SysUserService userService;

  @Autowired private SysRoleService roleService;

  @Autowired private SysPostService postService;

  @Autowired private TokenService tokenService;

  /** 获取用户列表 */
  @PreAuthorize("@ss.hasPermi('system:user:list')")
  @GetMapping("/list")
  public JsonResult<IPage<SysUser>> list(SysUser user) {
    IPage<SysUser> list = userService.selectUserList(user);
    return JsonResult.success(list);
  }

  @Log(title = "用户管理", businessType = BusinessType.EXPORT)
  @PreAuthorize("@ss.hasPermi('system:user:export')")
  @GetMapping("/export")
  public JsonResult export(SysUser user) {
    IPage<SysUser> list = userService.selectUserList(user);
    ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
    return util.exportExcel(list.getRecords(), "用户数据");
  }

  @Log(title = "用户管理", businessType = BusinessType.IMPORT)
  @PreAuthorize("@ss.hasPermi('system:user:import')")
  @PostMapping("/importData")
  public JsonResult importData(MultipartFile file, boolean updateSupport) throws Exception {
    ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
    List<SysUser> userList = util.importExcel(file.getInputStream());
    LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
    String operName = loginUser.getUsername();
    String message = userService.importUser(userList, updateSupport, SecurityUtils.getUserId());
    return JsonResult.success(message);
  }

  @GetMapping("/importTemplate")
  public JsonResult importTemplate() {
    ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
    return util.importTemplateExcel("用户数据");
  }

  /** 根据用户编号获取详细信息 */
  @PreAuthorize("@ss.hasPermi('system:user:query')")
  @GetMapping(value = {"/", "/{userId}"})
  public JsonResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
    JsonResult ajax = JsonResult.success();
    List<SysRole> roles = roleService.selectRoleAll();
    Map<String, Object> result = new HashMap<>();
    result.put(
        "roles",
        SysUser.isAdmin(userId)
            ? roles
            : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
    result.put("posts", postService.selectPostAll());
    if (StringUtils.isNotNull(userId)) {
      result.put("data", userService.selectUserById(userId));
      result.put("postIds", postService.selectPostListByUserId(userId));
      result.put("roleIds", roleService.selectRoleListByUserId(userId));
    }
    return JsonResult.success(result);
  }

  /** 新增用户 */
  @PreAuthorize("@ss.hasPermi('system:user:add')")
  @Log(title = "用户管理", businessType = BusinessType.INSERT)
  @PostMapping
  public JsonResult add(@Validated @RequestBody SysUser user) {
    // 使用默认邮箱、默认部门
    user.setEmail("default@default.com");
//    user.setDeptId(100L);
    if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))) {
      return JsonResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
    } else if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
      return JsonResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
//    } else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
//      return JsonResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
    }
    boolean isWeakPassword = !CheckPwd.evalPWD(user.getPassword());
    if (isWeakPassword) {
      throw new CustomException(ErrorCode.WEAK_PASSWORD);
    }
    user.setCreateUser(SecurityUtils.getUserId());
    user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
    return success(userService.insertUser(user));
  }

  /** 修改用户 */
  @PreAuthorize("@ss.hasPermi('system:user:edit')")
  @Log(title = "用户管理", businessType = BusinessType.UPDATE)
  @PutMapping
  public JsonResult edit(@Validated @RequestBody SysUser user) {
    userService.checkUserAllowed(user);
    // 使用默认邮箱、默认部门
    user.setEmail("default@default.com");
    user.setDeptId(100L);
    if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
      return JsonResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
//    } else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
//      return JsonResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
    }
    user.setUpdateUser(SecurityUtils.getUserId());
    return success(userService.updateUser(user));
  }

  /** 删除用户 */
  @PreAuthorize("@ss.hasPermi('system:user:remove')")
  @Log(title = "用户管理", businessType = BusinessType.DELETE)
  @DeleteMapping("/{userIds}")
  public JsonResult remove(@PathVariable Long[] userIds) {
    return success(userService.deleteUserByIds(userIds));
  }

  /** 重置密码 */
  @PreAuthorize("@ss.hasPermi('system:user:edit')")
  @Log(title = "用户管理", businessType = BusinessType.UPDATE)
  @PutMapping("/resetPwd")
  public JsonResult resetPwd(@RequestBody SysUser user) {
    boolean isWeakPassword = !CheckPwd.evalPWD(user.getPassword());
    if (isWeakPassword) {
      throw new CustomException(ErrorCode.WEAK_PASSWORD);
    }
    userService.checkUserAllowed(user);
    user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
    user.setUpdateUser(SecurityUtils.getUserId());
    return success(userService.resetPwd(user));
  }

  /** 状态修改 */
  @PreAuthorize("@ss.hasPermi('system:user:edit')")
  @Log(title = "用户管理", businessType = BusinessType.UPDATE)
  @PutMapping("/changeStatus")
  public JsonResult changeStatus(@RequestBody SysUser user) {
    userService.checkUserAllowed(user);
    user.setUpdateUser(SecurityUtils.getUserId());
    return success(userService.updateUserStatus(user));
  }
}
