//package com.yjtech.wisdom.tourism.framework.web.service;
//
//import com.yjtech.wisdom.tourism.common.constant.Constants;
//import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
//import com.yjtech.wisdom.tourism.common.exception.CustomException;
//import com.yjtech.wisdom.tourism.common.exception.user.CaptchaException;
//import com.yjtech.wisdom.tourism.common.exception.user.CaptchaExpireException;
//import com.yjtech.wisdom.tourism.common.exception.user.UserPasswordNotMatchException;
//import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
//import com.yjtech.wisdom.tourism.common.utils.JsonUtil;
//import com.yjtech.wisdom.tourism.common.utils.JsonUtils;
//import com.yjtech.wisdom.tourism.common.utils.MessageUtils;
//import com.yjtech.wisdom.tourism.framework.manager.AsyncManager;
//import com.yjtech.wisdom.tourism.framework.manager.factory.AsyncFactory;
//import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
//import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.LoginUser;
//import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.ScreenLoginBody;
//import com.yjtech.wisdom.tourism.redis.RedisCache;
//import com.yjtech.wisdom.tourism.system.service.SysUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import javax.annotation.Resource;
//import java.util.Objects;
//import java.util.UUID;
//
///**
// * 登录校验方法
// *
// * @author liuhong
// */
//@Component
//public class BigScreenLoginService {
//    @Autowired
//    private TokenService tokenService;
//
//    @Resource
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private RedisCache redisCache;
//
//
//
//    /**
//     * 登录验证
//     *
//     * @param loginBody
//     * @return 结果
//     */
//
//    public String login(ScreenLoginBody loginBody) throws Exception {
//
//        // 用户验证
//        Authentication authentication = null;
//        try {
//            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
//            authentication =
//                    authenticationManager.authenticate(
//                            new UsernamePasswordAuthenticationToken(username, password));
//        } catch (Exception e) {
//            if (e instanceof BadCredentialsException) {
//                AsyncManager.me()
//                        .execute(
//                                AsyncFactory.recordLogininfor(
//                                        username,
//                                        Constants.LOGIN_FAIL,
//                                        MessageUtils.message("user.password.not.match")));
//                throw new UserPasswordNotMatchException();
//            } else {
//                AsyncManager.me()
//                        .execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
//                throw new CustomException(e.getMessage());
//            }
//        }
//        AsyncManager.me()
//                .execute(
//                        AsyncFactory.recordLogininfor(
//                                username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
//        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
//
//        // 保存pushToken-推送消息唯一标识
//        if (!StringUtils.isEmpty(pushToken)) {
//            SysUser sysUser = new SysUser();
//            sysUser.setUserId(loginUser.getUser().getUserId());
//            sysUser.setPushToken(pushToken);
//            userService.updateUserProfile(sysUser);
//        }
//
//        loginUser.setTokenType(tokenType);
//        // 生成token
//        return tokenService.createToken(loginUser);
//    }
//
//}
