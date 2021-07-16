package com.yjtech.wisdom.tourism.common.validator;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by wuyongchong on 2019/10/11.
 */
public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {

  private Logger log = LoggerFactory.getLogger(this.getClass());

  private List<Object> values = new ArrayList<>();

  @Override
  public void initialize(EnumValue constraintAnnotation) {
    if (!ArrayUtils.isEmpty(constraintAnnotation.values())) {
      this.values.addAll(Arrays.asList(constraintAnnotation.values()));
    }
    Class<?>[] enumClzArr = constraintAnnotation.enumClz();
    try {
      for (Class<?> enumClz : enumClzArr) {
        if (!enumClz.isEnum()) {
          throw new Exception("enumClz必须为枚举");
        }
        Method method = enumClz.getMethod("getValue");
        if (Objects.isNull(method)) {
          throw new Exception(String.format("枚举对象{}缺少字段名为value的字段",
              enumClz.getName()));
        }
        Object[] enumOjects = enumClz.getEnumConstants();
        for (Object obj : enumOjects) {
          values.add(method.invoke(obj));
        }
      }
    } catch (Exception e) {
      log.error("[处理枚举校验异常]", e);
    }
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
    for (Object s : values) {
      if (String.valueOf(s).equals(String.valueOf(value))) {
        return true;
      }
    }
    return false;
  }
}
