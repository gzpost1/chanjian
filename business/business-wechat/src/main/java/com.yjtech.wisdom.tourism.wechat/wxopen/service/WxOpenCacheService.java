package com.yjtech.wisdom.tourism.wechat.wxopen.service;

import com.yjtech.wisdom.tourism.wechat.wxopen.config.WxOpenProperties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by wuyongchong on 2019/9/5.
 */
public class WxOpenCacheService {

  public final static String COMPONENT_VERIFY_TICKET_KEY = "componentVerifyTicket";

  public final static String COMPONENT_ACCESS_TOKEN_KEY = "componentAccessToken";

  private final static String AUTHORIZER_REFRESH_TOKEN_KEY_PRIFIX = "wechat_authorizer_refresh_token:";
  private final static String AUTHORIZER_ACCESS_TOKEN_KEY_PRIFIX = "wechat_authorizer_access_token:";

  private final WxOpenProperties wxOpenProperties;

  private final StringRedisTemplate stringRedisTemplate;

  public WxOpenCacheService(
      WxOpenProperties wxOpenProperties,
      StringRedisTemplate stringRedisTemplate) {
    this.wxOpenProperties = wxOpenProperties;
    this.stringRedisTemplate = stringRedisTemplate;
  }

  public String getKeyPrefix() {
    if (StringUtils.isBlank(wxOpenProperties.getCacheKeyPrefix())) {
      return "";
    } else {
      return StringUtils.removeEnd(wxOpenProperties.getCacheKeyPrefix(), ":") + ":";
    }
  }

  public String getCacheKey(String key) {
    if (StringUtils.isBlank(key)) {
      return null;
    }
    return getKeyPrefix() + key;
  }

  public void setComponentVerifyTicket(String componentVerifyTicket) {
    stringRedisTemplate.opsForValue()
        .set(getCacheKey(COMPONENT_VERIFY_TICKET_KEY), componentVerifyTicket);
  }

  public String getComponentVerifyTicket() {
    return stringRedisTemplate.opsForValue().get(getCacheKey(COMPONENT_VERIFY_TICKET_KEY));
  }


  public String getComponentAccessToken() {
    return stringRedisTemplate.opsForValue().get(getCacheKey(COMPONENT_ACCESS_TOKEN_KEY));
  }

  public boolean isComponentAccessTokenExpired() {
    String val = stringRedisTemplate.opsForValue().get(getCacheKey(COMPONENT_ACCESS_TOKEN_KEY));
    if (StringUtils.isBlank(val)) {
      return true;
    }
    return false;
  }

  public void expireComponentAccessToken() {
    //stringRedisTemplate.expire(getCacheKey(COMPONENT_ACCESS_TOKEN_KEY),0, TimeUnit.SECONDS);
    stringRedisTemplate.delete(getCacheKey(COMPONENT_ACCESS_TOKEN_KEY));
  }

  public void updateComponentAccessToken(String componentAccessToken, int expiresInSeconds) {
    stringRedisTemplate.opsForValue()
        .set(getCacheKey(COMPONENT_ACCESS_TOKEN_KEY), componentAccessToken, expiresInSeconds - 200,
            TimeUnit.SECONDS);
  }


  public void setAuthorizerRefreshToken(String appId, String authorizerRefreshToken) {
    stringRedisTemplate.opsForValue()
        .set(getCacheKey(AUTHORIZER_REFRESH_TOKEN_KEY_PRIFIX + appId), authorizerRefreshToken);
  }

  public String getAuthorizerRefreshToken(String appId) {
    return stringRedisTemplate.opsForValue()
        .get(getCacheKey(AUTHORIZER_REFRESH_TOKEN_KEY_PRIFIX + appId));
  }

  public void updateAuthorizerAccessToken(String appId, String authorizerAccessToken,
      int expiresInSeconds) {
    stringRedisTemplate.opsForValue()
        .set(getCacheKey(AUTHORIZER_ACCESS_TOKEN_KEY_PRIFIX + appId), authorizerAccessToken,
            expiresInSeconds - 200,
            TimeUnit.SECONDS);
  }

  public boolean isAuthorizerAccessTokenExpired(String appId) {
    String val = stringRedisTemplate.opsForValue()
        .get(getCacheKey(AUTHORIZER_ACCESS_TOKEN_KEY_PRIFIX + appId));
    if (StringUtils.isBlank(val)) {
      return true;
    }
    return false;
  }

  public void expireAuthorizerAccessToken(String appId) {
    stringRedisTemplate.delete(getCacheKey(AUTHORIZER_ACCESS_TOKEN_KEY_PRIFIX + appId));
  }


  public String getAuthorizerAccessToken(String appId) {
    return stringRedisTemplate.opsForValue()
        .get(getCacheKey(AUTHORIZER_ACCESS_TOKEN_KEY_PRIFIX + appId));
  }


}
