package com.yjtech.wisdom.tourism.portal.controller.system;

import com.yjtech.wisdom.tourism.common.annotation.Log;
import com.yjtech.wisdom.tourism.common.config.AppConfig;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.enums.BusinessType;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.ServletUtils;
import com.yjtech.wisdom.tourism.common.utils.file.FileUploadUtils;
import com.yjtech.wisdom.tourism.common.utils.password.CheckPwd;
import com.yjtech.wisdom.tourism.framework.web.service.TokenService;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseController;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.LoginUser;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 个人信息 业务处理
 *
 * @author liuhong
 */
@RestController
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController {
    @Autowired
    private SysUserService userService;

    @Autowired
    private TokenService tokenService;

    /**
     * 个人信息
     */
    @GetMapping
    public JsonResult profile() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        Map<String, Object> result = new HashMap<>();
        result.put("roleGroup", userService.selectUserRoleGroup(loginUser.getUsername()));
        result.put("postGroup", userService.selectUserPostGroup(loginUser.getUsername()));
        result.put("user", user);
        return JsonResult.success(result);
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public JsonResult updateProfile(@RequestBody SysUser user) {
        if (userService.updateUserProfile(user) > 0) {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            // 更新缓存用户信息
            loginUser.getUser().setNickName(user.getNickName());
            loginUser.getUser().setPhonenumber(user.getPhonenumber());
            loginUser.getUser().setEmail(user.getEmail());
            loginUser.getUser().setSex(user.getSex());
            tokenService.setLoginUser(loginUser);
            return JsonResult.success();
        }
        return JsonResult.error("修改个人信息异常，请联系管理员");
    }

    /**
     * 修改密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public JsonResult updatePwd(String oldPassword, String newPassword) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String userName = loginUser.getUsername();
        String password = loginUser.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password)) {
            return JsonResult.error("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password)) {
            return JsonResult.error("新密码不能与旧密码相同");
        }
        boolean isWeakPassword = !CheckPwd.evalPWD(newPassword);
        if (isWeakPassword) {
            throw new CustomException(ErrorCode.WEAK_PASSWORD);
        }
        if (userService.resetUserPwd(userName, SecurityUtils.encryptPassword(newPassword)) > 0) {
            // 更新缓存用户密码
            loginUser.getUser().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return JsonResult.success();
        }
        return JsonResult.error("修改密码异常，请联系管理员");
    }

    /**
     * 头像上传
     */
    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    public JsonResult avatar(@RequestParam("avatarfile") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String avatar = FileUploadUtils.upload(AppConfig.getAvatarPath(), file);
            if (userService.updateUserAvatar(loginUser.getUsername(), avatar)) {
                Map<String, Object> result = new HashMap<>();
                result.put("imgUrl", avatar);
                // 更新缓存用户头像
                loginUser.getUser().setAvatar(avatar);
                tokenService.setLoginUser(loginUser);
                return JsonResult.success(result);
            }
        }
        return JsonResult.error("上传图片异常，请联系管理员");
    }
}
