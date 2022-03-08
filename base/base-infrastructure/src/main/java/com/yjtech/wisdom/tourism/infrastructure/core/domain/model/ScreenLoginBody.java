package com.yjtech.wisdom.tourism.infrastructure.core.domain.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用户登录对象
 *
 * @author Mujun
 */
@Data
public class ScreenLoginBody {
    /**
     * 手机号
     */
    @NotNull(message = "手机号不能为空")
    private String phone;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 短信验证码
     */
    private String phoneCode;
}
