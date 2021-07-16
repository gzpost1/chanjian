package com.yjtech.wisdom.tourism.wechat.wxopen.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by wuyongchong on 2019/9/5.
 */
@ConfigurationProperties(prefix = "wechat.open")
public class WxOpenProperties {

  /**
   * 设置微信三方平台的appid
   */
  private String componentAppId;

  /**
   * 设置微信三方平台的app secret
   */
  private String componentAppSecret;

  /**
   * 设置微信三方平台的token
   */
  private String componentToken;

  /**
   * 设置微信三方平台的EncodingAESKey
   */
  private String componentAesKey;

/**
 * 授权地址URL
 */
private String gotoAuthUrl;

  /**
   * 授权回调URL
   */
  private String authRedirectURI;

  /**
   * 配置业务域名
   */
  private List<String> webViewDomainList;

  /**
   * 服务器域名
   */
  private List<String> requestDomainList;
  private List<String> wsrequestDomainList;
  private List<String> uploadDomainList;
  private List<String> downloadDomainList;

  /**
   * 缓存前缀
   */
  private String cacheKeyPrefix;


  public String getComponentAppId() {
    return componentAppId;
  }

  public void setComponentAppId(String componentAppId) {
    this.componentAppId = componentAppId;
  }

  public String getComponentAppSecret() {
    return componentAppSecret;
  }

  public void setComponentAppSecret(String componentAppSecret) {
    this.componentAppSecret = componentAppSecret;
  }

  public String getComponentToken() {
    return componentToken;
  }

  public void setComponentToken(String componentToken) {
    this.componentToken = componentToken;
  }

  public String getComponentAesKey() {
    return componentAesKey;
  }

  public void setComponentAesKey(String componentAesKey) {
    this.componentAesKey = componentAesKey;
  }

  public String getAuthRedirectURI() {
    return authRedirectURI;
  }

  public void setAuthRedirectURI(String authRedirectURI) {
    this.authRedirectURI = authRedirectURI;
  }

  public String getCacheKeyPrefix() {
    return cacheKeyPrefix;
  }

  public void setCacheKeyPrefix(String cacheKeyPrefix) {
    this.cacheKeyPrefix = cacheKeyPrefix;
  }

  public List<String> getWebViewDomainList() {
    return webViewDomainList;
  }

  public void setWebViewDomainList(List<String> webViewDomainList) {
    this.webViewDomainList = webViewDomainList;
  }

  public List<String> getRequestDomainList() {
    return requestDomainList;
  }

  public void setRequestDomainList(List<String> requestDomainList) {
    this.requestDomainList = requestDomainList;
  }

  public List<String> getWsrequestDomainList() {
    return wsrequestDomainList;
  }

  public void setWsrequestDomainList(List<String> wsrequestDomainList) {
    this.wsrequestDomainList = wsrequestDomainList;
  }

  public List<String> getUploadDomainList() {
    return uploadDomainList;
  }

  public void setUploadDomainList(List<String> uploadDomainList) {
    this.uploadDomainList = uploadDomainList;
  }

  public List<String> getDownloadDomainList() {
    return downloadDomainList;
  }

  public void setDownloadDomainList(List<String> downloadDomainList) {
    this.downloadDomainList = downloadDomainList;
  }

  public String getGotoAuthUrl() {
    return gotoAuthUrl;
  }

  public void setGotoAuthUrl(String gotoAuthUrl) {
    this.gotoAuthUrl = gotoAuthUrl;
  }
}
