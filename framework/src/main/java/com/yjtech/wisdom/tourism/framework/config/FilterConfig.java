package com.yjtech.wisdom.tourism.framework.config;

import com.yjtech.wisdom.tourism.common.filter.RepeatableFilter;
import com.yjtech.wisdom.tourism.common.filter.XssFilter;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.Map;

/**
 * Filter配置
 *
 * @author liuhong
 */
@Configuration
public class FilterConfig {
  @Value("${xss.enabled}")
  private String enabled;

  @Value("${xss.excludes}")
  private String excludes;

  @Value("${xss.urlPatterns}")
  private String urlPatterns;

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Bean
  public FilterRegistrationBean xssFilterRegistration() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setDispatcherTypes(DispatcherType.REQUEST);
    registration.setFilter(new XssFilter());
    registration.addUrlPatterns(StringUtils.split(urlPatterns, ","));
    registration.setName("xssFilter");
    registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
    Map<String, String> initParameters = new HashMap<String, String>();
    initParameters.put("excludes", excludes);
    initParameters.put("enabled", enabled);
    registration.setInitParameters(initParameters);
    return registration;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Bean
  public FilterRegistrationBean someFilterRegistration() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(new RepeatableFilter());
    registration.addUrlPatterns("/*");
    registration.setName("repeatableFilter");
    registration.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
    return registration;
  }
}
