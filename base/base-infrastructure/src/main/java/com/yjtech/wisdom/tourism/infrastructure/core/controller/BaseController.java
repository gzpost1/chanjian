package com.yjtech.wisdom.tourism.infrastructure.core.controller;

import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.DateUtils;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.infrastructure.core.page.PageDomain;
import com.yjtech.wisdom.tourism.infrastructure.core.page.TableSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * web层通用数据处理
 *
 * @author liuhong
 */
public class BaseController {
  protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

  /** 将前台传递过来的日期格式的字符串，自动转化为Date类型 */
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    // Date 类型转换
    binder.registerCustomEditor(
        Date.class,
        new PropertyEditorSupport() {
          @Override
          public void setAsText(String text) {
            setValue(DateUtils.parseDate(text));
          }
        });
  }

  /** 设置请求分页数据 */
  protected PageDomain startPage() {
    return TableSupport.buildPageRequest();
  }

  /**
   * 响应返回结果
   *
   * @return 操作结果
   */
  protected JsonResult success() {
    return JsonResult.success();
  }

  /**
   * 响应返回结果
   *
   * @return 操作结果
   */
  protected <T> JsonResult<T> success(T t) {
    return JsonResult.success(t);
  }

  /** 页面跳转 */
  public String redirect(String url) {
    return StringUtils.format("redirect:{}", url);
  }
}
