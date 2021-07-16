package com.yjtech.wisdom.tourism.wechat.wxpay.config;

import com.yjtech.wisdom.tourism.wechat.wxpay.service.WxPayService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wuyongchong on 2019/9/16.
 */
@Configuration
@EnableConfigurationProperties(WxPayProperties.class)
@ConditionalOnClass(WxPayService.class)
@ConditionalOnProperty(prefix = "wx.pay", value = "enabled", matchIfMissing = true)
public class WxPayAutoConfiguration {

  @Autowired
  private WxPayProperties properties;

  @Bean
  @ConditionalOnMissingBean(WxPayService.class)
  public WxPayService wxPayService() {
    WxPayConfig payConfig = new WxPayConfig();
    payConfig.setAppId(StringUtils.trimToNull(this.properties.getAppId()));
    payConfig.setMchId(StringUtils.trimToNull(this.properties.getMchId()));
    payConfig.setMchKey(StringUtils.trimToNull(this.properties.getMchKey()));
    payConfig.setSubAppId(StringUtils.trimToNull(this.properties.getSubAppId()));
    payConfig.setSubMchId(StringUtils.trimToNull(this.properties.getSubMchId()));
    payConfig.setKeyPath(StringUtils.trimToNull(this.properties.getKeyPath()));
    // 可以指定是否使用沙箱环境
    payConfig.setUseSandboxEnv(false);

    WxPayService wxPayService = new WxPayService();
    wxPayService.setConfig(payConfig);
    return wxPayService;
  }
}
