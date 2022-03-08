package com.yjtech.wisdom.tourism.common.validator;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wuyongchong on 2019/8/21.
 */
public class IsMobileValidator implements ConstraintValidator< IsMobile, String> {

  private boolean required = false;

  private static final Pattern mobile_pattern = Pattern.compile("^1[3456789]\\d{9}$");

  //工具方法，判断是否是手机号
  public static boolean isMobile(String src) {
    if (StringUtils.isEmpty(src)) {
      return false;
    }
    Matcher m = mobile_pattern.matcher(src);
    return m.matches();
  }

  @Override
  public void initialize(IsMobile constraintAnnotation) {
    required = constraintAnnotation.isRequired();
  }

  @Override
  public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
    //是否为手机号的实现
    if (required) {
      return isMobile(phone);
    } else {
      if (StringUtils.isEmpty(phone)) {
        return true;
      } else {
        return isMobile(phone);
      }
    }
  }
}
