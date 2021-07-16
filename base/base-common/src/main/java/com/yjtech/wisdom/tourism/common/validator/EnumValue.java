package com.yjtech.wisdom.tourism.common.validator;


import com.yjtech.wisdom.tourism.common.constant.EnumInterface;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by yangyingcan on 2019/10/11.
 */

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE,
    ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(EnumValue.List.class)
@Documented
@Constraint(validatedBy = {EnumValueValidator.class})
public @interface EnumValue {

  //默认错误消息
  String message() default "必须为指定值";

  String[] values() default {};

  Class<? extends EnumInterface>[] enumClz() default {};

  //分组
  Class<?>[] groups() default {};

  //负载
  Class<? extends Payload>[] payload() default {};

  @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE,
      ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public @interface List {

    EnumValue[] value();
  }
}