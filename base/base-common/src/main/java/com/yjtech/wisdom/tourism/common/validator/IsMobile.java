package com.yjtech.wisdom.tourism.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
//注解的实现类。
@Constraint(validatedBy = {IsMobileValidator.class})
public @interface IsMobile {

  //校验错误的默认信息
  String message() default "手机号码格式有问题";

  //是否强制校验
  boolean isRequired() default false;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}