package com.yjtech.wisdom.tourism.framework.web.exception;

import com.yjtech.wisdom.tourism.common.constant.HttpStatus;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.exception.BaseException;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.exception.user.CaptchaException;
import com.yjtech.wisdom.tourism.common.exception.user.CaptchaExpireException;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理器
 *
 * @author liuhong
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /** 基础异常 */
  @ExceptionHandler(BaseException.class)
  public JsonResult baseException(BaseException e) {
    return JsonResult.error(e.getMessage());
  }

  /** 业务异常 */
  @ExceptionHandler(CustomException.class)
  public JsonResult businessException(CustomException e) {
    if (StringUtils.isNull(e.getCode())) {
      return JsonResult.error(e.getMessage());
    }
    return JsonResult.error(e.getCode(), e.getMessage());
  }

  /** 验证码异常 */
  @ExceptionHandler(CaptchaException.class)
  public JsonResult captchaException(CaptchaException e) {
    return JsonResult.error(ErrorCode.INPUT_CAPTCHA_ERROR.getCode(), e.getMessage());
  }

  @ExceptionHandler(CaptchaExpireException.class)
  public JsonResult captchaExpireException(CaptchaExpireException e) {
    return JsonResult.error(ErrorCode.CAPTCHA_EXPIRE.getCode(), e.getMessage());
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public JsonResult handlerNoFoundException(NoHandlerFoundException e) {
    log.error(e.getMessage(), e);
    return JsonResult.error(HttpStatus.NOT_FOUND, "路径不存在，请检查路径是否正确");
  }

  @ExceptionHandler(AccessDeniedException.class)
  public JsonResult handleAuthorizationException(AccessDeniedException e) {
    log.error(e.getMessage());
    return JsonResult.error(HttpStatus.FORBIDDEN, "没有权限，请联系管理员授权");
  }

  @ExceptionHandler(AccountExpiredException.class)
  public JsonResult handleAccountExpiredException(AccountExpiredException e) {
    log.error(e.getMessage(), e);
    return JsonResult.error(e.getMessage());
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public JsonResult handleUsernameNotFoundException(UsernameNotFoundException e) {
    log.error(e.getMessage(), e);
    return JsonResult.error(e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public JsonResult handleException(Exception e) {
    log.error(e.getMessage(), e);
    return JsonResult.error(e.getMessage());
  }

  /** 自定义验证异常 */
  @ExceptionHandler(BindException.class)
  public JsonResult validatedBindException(BindException e) {
    log.error(e.getMessage(), e);
    String message = e.getAllErrors().get(0).getDefaultMessage();
    return JsonResult.error(message);
  }

  /** 自定义验证异常 */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Object validExceptionHandler(MethodArgumentNotValidException e) {
    log.error(e.getMessage(), e);
    String message = e.getBindingResult().getFieldError().getDefaultMessage();
    return JsonResult.error(message);
  }
}
