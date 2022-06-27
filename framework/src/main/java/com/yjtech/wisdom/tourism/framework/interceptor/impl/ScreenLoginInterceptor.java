package com.yjtech.wisdom.tourism.framework.interceptor.impl;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yjtech.wisdom.tourism.common.annotation.IgnoreAuth;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.filter.RepeatedlyRequestWrapper;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.common.utils.http.HttpHelper;
import com.yjtech.wisdom.tourism.framework.interceptor.RepeatSubmitInterceptor;
import com.yjtech.wisdom.tourism.framework.web.service.ScreenTokenService;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.ScreenLoginUser;
import com.yjtech.wisdom.tourism.infrastructure.utils.TokenUtils;
import com.yjtech.wisdom.tourism.redis.RedisCache;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 大屏登陆拦截
 *
 * @author liuhong
 */
@Component
public class ScreenLoginInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    ScreenTokenService screenTokenService;

    @Override
    //重写本方法
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod method = (HandlerMethod) handler;
        IgnoreAuth ignoreAuth = method.getMethodAnnotation(IgnoreAuth.class);
        if (Objects.nonNull(ignoreAuth)) {
            return true;
        }
        //获取请求头中的数据
        String authorization = request.getHeader("Authorization");
        //判断token是否合法，如果不合法则抛出自定义异常，合法则返回true继续执行代码
        if (StringUtils.isEmpty(authorization)) {
            throw new CustomException(ErrorCode.TOKEN_NULL);
        }
        ScreenLoginUser loginUser = screenTokenService.getLoginUser(request);
        if(!Objects.equals(loginUser.getAuditStatus(),1)){
            //注册流程改变，快速注册直接成功，屏蔽该异常
            //throw new CustomException("该企业尚未完成注册！");
        }
        if (loginUser == null) {
            throw new CustomException(ErrorCode.ERROR_NO_LOGIN);
        }
        return true;
    }
}
