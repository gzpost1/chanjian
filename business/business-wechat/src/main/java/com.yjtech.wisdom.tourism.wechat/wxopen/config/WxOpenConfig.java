package com.yjtech.wisdom.tourism.wechat.wxopen.config;

import com.yjtech.wisdom.tourism.wechat.wxopen.service.WxOpenCacheService;
import com.yjtech.wisdom.tourism.wechat.wxopen.service.WxOpenComponentService;
import com.yjtech.wisdom.tourism.wechat.wxopen.service.WxOpenFastMaService;
import com.yjtech.wisdom.tourism.wechat.wxopen.service.WxOpenKefuService;
import com.yjtech.wisdom.tourism.wechat.wxopen.service.WxOpenMaService;
import com.yjtech.wisdom.tourism.wechat.wxopen.service.WxOpenTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by wuyongchong on 2019/9/5.
 */
@Configuration
@EnableConfigurationProperties({WxOpenProperties.class})
public class WxOpenConfig {

  @Autowired
  private WxOpenProperties wxOpenProperties;

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  @Bean
  public WxOpenCacheService wxOpenCacheService() {
    return new WxOpenCacheService(wxOpenProperties, stringRedisTemplate);
  }

  @Bean
  public WxOpenComponentService wxOpenComponentService() {
    return new WxOpenComponentService(wxOpenProperties, wxOpenCacheService());
  }

  @Bean
  public WxOpenFastMaService wxOpenFastMaService(WxOpenComponentService wxOpenComponentService) {
    return new WxOpenFastMaService(wxOpenComponentService);
  }

  @Bean
  public WxOpenMaService wxOpenMaService(WxOpenComponentService wxOpenComponentService) {
    return new WxOpenMaService(wxOpenComponentService);
  }

  @Bean
  public WxOpenTemplateService wxOpenTemplateService(
      WxOpenComponentService wxOpenComponentService) {
    return new WxOpenTemplateService(wxOpenComponentService);
  }

  @Bean
  public WxOpenKefuService wxOpenKefuService(WxOpenComponentService wxOpenComponentService) {
    return new WxOpenKefuService(wxOpenComponentService);
  }

}
