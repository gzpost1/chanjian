package com.yjtech.wisdom.tourism.common.annotation;

import java.lang.annotation.*;
import java.math.BigDecimal;

/**
 * 自定义导出Excel数据注解
 *
 * @author liuhong
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
@Documented
public @interface ExcelRead {

  /**
   * 行号
   * @return
   */
  public int rowNum() default 0;

  /**
   * 列号
   * @return
   */
  public int cellNum() default 0;
}
