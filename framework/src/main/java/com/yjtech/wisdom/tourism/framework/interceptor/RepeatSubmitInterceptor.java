package com.yjtech.wisdom.tourism.framework.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.yjtech.wisdom.tourism.common.annotation.RepeatSubmit;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.ServletUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 防止重复提交拦截器
 *
 * @author liuhong
 */
@Component
public abstract class RepeatSubmitInterceptor extends HandlerInterceptorAdapter {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (handler instanceof HandlerMethod) {
      HandlerMethod handlerMethod = (HandlerMethod) handler;
      Method method = handlerMethod.getMethod();
      RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
      if (annotation != null) {
        if (this.isRepeatSubmit(request)) {
          JsonResult ajaxResult = JsonResult.error(ErrorCode.UNKNOWN.code(), "不允许重复提交，请稍后再试");
          ServletUtils.renderString(response, JSONObject.toJSONString(ajaxResult));
          return false;
        }
      }
      return true;
    } else {
      return super.preHandle(request, response, handler);
    }
  }

  /**
   * 验证是否重复提交由子类实现具体的防重复提交的规则
   *
   * @param request
   * @return
   * @throws Exception
   */
  public abstract boolean isRepeatSubmit(HttpServletRequest request);
}
