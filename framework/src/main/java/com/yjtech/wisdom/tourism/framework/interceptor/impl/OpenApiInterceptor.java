package com.yjtech.wisdom.tourism.framework.interceptor.impl;

import com.yjtech.wisdom.tourism.common.annotation.IgnoreAuth;
import com.yjtech.wisdom.tourism.common.config.OpenApiConfig;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.framework.web.service.ScreenTokenService;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.ScreenLoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 *
 * @author Mujun
 */
@Component
public class OpenApiInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    OpenApiConfig openApiConfig;

    @Override
    //重写本方法
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        //获取请求头中的数据
        String userOpenToken = request.getHeader("open-token");
        String token = openApiConfig.getToken();
        AssertUtil.isFalse(Objects.isNull(userOpenToken)&&!Objects.equals(token,userOpenToken),"请咨询系统管理员获取正确的访问权限");
        return true;
    }
}
