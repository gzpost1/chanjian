package com.yjtech.wisdom.tourism.framework.web.service;

import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.user.CaptchaException;
import com.yjtech.wisdom.tourism.common.exception.user.CaptchaExpireException;
import com.yjtech.wisdom.tourism.common.exception.user.UserPasswordNotMatchException;
import com.yjtech.wisdom.tourism.common.utils.MessageUtils;
import com.yjtech.wisdom.tourism.framework.manager.AsyncManager;
import com.yjtech.wisdom.tourism.framework.manager.factory.AsyncFactory;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.LoginUser;
import com.yjtech.wisdom.tourism.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 登录校验方法
 *
 * @author liuhong
 */
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid,Boolean appUser) throws Exception {
        //App用户不用验证码登录
//        if (Objects.isNull(appUser) || !appUser) {
//            String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
//            String captcha = redisCache.getCacheObject(verifyKey);
//            redisCache.deleteObject(verifyKey);
//            if (captcha == null) {
//                AsyncManager.me()
//                        .execute(
//                                AsyncFactory.recordLogininfor(
//                                        username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
//                throw new CaptchaExpireException();
//            }
//            if (!code.equalsIgnoreCase(captcha)) {
//                AsyncManager.me()
//                        .execute(
//                                AsyncFactory.recordLogininfor(
//                                        username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
//                throw new CaptchaException();
//            }
//        }
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me()
                        .execute(
                                AsyncFactory.recordLogininfor(
                                        username,
                                        Constants.LOGIN_FAIL,
                                        MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me()
                        .execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me()
                .execute(
                        AsyncFactory.recordLogininfor(
                                username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
    }
}
