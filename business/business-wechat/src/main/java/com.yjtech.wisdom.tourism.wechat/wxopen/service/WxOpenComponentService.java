package com.yjtech.wisdom.tourism.wechat.wxopen.service;

import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_AUTHORIZER_TOKEN_URL;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_COMPONENT_TOKEN_URL;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_CREATE_PREAUTHCODE_URL;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_GET_AUTHORIZER_INFO_URL;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_GET_AUTHORIZER_LIST;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_GET_AUTHORIZER_OPTION_URL;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_QUERY_AUTH_URL;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_SET_AUTHORIZER_OPTION_URL;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.COMPONENT_LOGIN_PAGE_URL;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.COMPONENT_MOBILE_LOGIN_PAGE_URL;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.MINIAPP_JSCODE_2_SESSION;

import com.yjtech.wisdom.tourism.wechat.wxopen.bean.WxOpenAuthorizerAccessToken;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.WxOpenComponentAccessToken;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.auth.WxOpenAuthorizationInfo;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxMaJscode2SessionResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxOpenAuthorizerInfoResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxOpenAuthorizerListResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxOpenAuthorizerOptionResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxOpenQueryAuthResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.config.WxOpenProperties;
import com.yjtech.wisdom.tourism.wechat.wxcommon.error.WxError;
import com.yjtech.wisdom.tourism.wechat.wxcommon.error.WxErrorException;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.URIUtil;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.crypto.SHA1;
import com.yjtech.wisdom.tourism.wechat.wxcommon.util.json.WxGsonBuilder;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.json.WxOpenGsonBuilder;
import com.google.gson.JsonObject;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wuyongchong on 2019/9/5.
 */
public class WxOpenComponentService extends AbstractWxOpenService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final WxOpenProperties wxOpenProperties;

  private final WxOpenCacheService wxOpenCacheService;

  public WxOpenComponentService(
      WxOpenProperties wxOpenProperties,
      WxOpenCacheService wxOpenCacheService) {
    this.wxOpenProperties = wxOpenProperties;
    this.wxOpenCacheService = wxOpenCacheService;
  }

  public WxOpenProperties getWxOpenProperties() {
    return wxOpenProperties;
  }


  private String httpPost(String uri, String postData) throws WxErrorException {
    String componentAccessToken = getComponentAccessToken(false);
    return httpPost(uri, postData, "component_access_token", componentAccessToken);
  }

  private String httpPost(String uri, String postData, String accessTokenKey, String accessTokenVal)
      throws WxErrorException {
    String uriWithComponentAccessToken =
        uri + (uri.contains("?") ? "&" : "?") + accessTokenKey + "=" + accessTokenVal;
    try {
      return doHttpPost(uriWithComponentAccessToken, postData);
    } catch (WxErrorException e) {
      WxError error = e.getError();
      /*
       * 发生以下情况时尝试刷新access_token
       * 40001 获取access_token时AppSecret错误，或者access_token无效
       * 42001 access_token超时
       * 40014 不合法的access_token，请开发者认真比对access_token的有效性（如是否过期），或查看是否正在为恰当的公众号调用接口
       */
      if (error.getErrorCode() == 42001 || error.getErrorCode() == 40001
          || error.getErrorCode() == 40014) {
        // 强制设置wxMpConfigStorage它的access token过期了，这样在下一次请求里就会刷新access token
        wxOpenCacheService.expireComponentAccessToken();
      }
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error, e);
      }
      return null;
    }
  }

  private String httpGet(String uri)
      throws WxErrorException {
    String componentAccessToken = getComponentAccessToken(false);
    return httpGet(uri, "component_access_token", componentAccessToken);
  }

  private String httpGet(String uri, String accessTokenKey, String accessTokenVal)
      throws WxErrorException {
    String uriWithComponentAccessToken =
        uri + (uri.contains("?") ? "&" : "?") + accessTokenKey + "=" + accessTokenVal;
    try {
      return doHttpGet(uriWithComponentAccessToken);
    } catch (WxErrorException e) {
      WxError error = e.getError();
      /*
       * 发生以下情况时尝试刷新access_token
       * 40001 获取access_token时AppSecret错误，或者access_token无效
       * 42001 access_token超时
       * 40014 不合法的access_token，请开发者认真比对access_token的有效性（如是否过期），或查看是否正在为恰当的公众号调用接口
       */
      if (error.getErrorCode() == 42001 || error.getErrorCode() == 40001
          || error.getErrorCode() == 40014) {
        // 强制设置wxMpConfigStorage它的access token过期了，这样在下一次请求里就会刷新access token
        wxOpenCacheService.expireComponentAccessToken();
      }
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error, e);
      }
      return null;
    }
  }

  public boolean checkSignature(String timestamp, String nonce, String signature) {
    try {
      return SHA1.gen(wxOpenProperties.getComponentToken(), timestamp, nonce)
          .equals(signature);
    } catch (Exception e) {
      logger.error("Checking signature failed, and the reason is :" + e.getMessage());
      return false;
    }
  }

  public void setComponentVerifyTicket(String componentVerifyTicket) {
    wxOpenCacheService.setComponentVerifyTicket(componentVerifyTicket);
  }

  public String getComponentVerifyTicket() {
    return wxOpenCacheService.getComponentVerifyTicket();
  }

  /**
   * 获取第三方平台component_access_token
   */
  public String getComponentAccessToken(boolean forceRefresh) throws WxErrorException {
    if (wxOpenCacheService.isComponentAccessTokenExpired() || forceRefresh) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("component_appid", wxOpenProperties.getComponentAppId());
      jsonObject.addProperty("component_appsecret", wxOpenProperties.getComponentAppSecret());
      jsonObject
          .addProperty("component_verify_ticket", wxOpenCacheService.getComponentVerifyTicket());

      String responseContent = doHttpPost(API_COMPONENT_TOKEN_URL, jsonObject.toString());

      WxOpenComponentAccessToken componentAccessToken = WxOpenComponentAccessToken
          .fromJson(responseContent);

      wxOpenCacheService.updateComponentAccessToken(componentAccessToken.getComponentAccessToken(),
          componentAccessToken.getExpiresIn());
    }
    return wxOpenCacheService.getComponentAccessToken();
  }

  public String getPreAuthUrl(String redirectURI) throws WxErrorException {
    return getPreAuthUrl(redirectURI, null, null);
  }

  public String getPreAuthUrl(String redirectURI, String authType, String bizAppid)
      throws WxErrorException {
    return createPreAuthUrl(redirectURI, authType, bizAppid, false);
  }

  public String getMobilePreAuthUrl(String redirectURI) throws WxErrorException {
    return getMobilePreAuthUrl(redirectURI, null, null);
  }

  public String getMobilePreAuthUrl(String redirectURI, String authType, String bizAppid)
      throws WxErrorException {
    return createPreAuthUrl(redirectURI, authType, bizAppid, true);
  }

  /**
   * 获取授权URL
   */
  private String createPreAuthUrl(String redirectURI, String authType, String bizAppid,
      boolean isMobile) throws WxErrorException {

    //获取预授权码pre_auth_code
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("component_appid", wxOpenProperties.getComponentAppId());
    String responseContent = httpPost(API_CREATE_PREAUTHCODE_URL, jsonObject.toString());
    jsonObject = WxGsonBuilder.create().fromJson(responseContent, JsonObject.class);
    String pre_auth_code = jsonObject.get("pre_auth_code").getAsString();

    //生成授权URL
    StringBuilder preAuthUrl = new StringBuilder(
        String.format((isMobile ? COMPONENT_MOBILE_LOGIN_PAGE_URL : COMPONENT_LOGIN_PAGE_URL),
            wxOpenProperties.getComponentAppId(), pre_auth_code,
            URIUtil.encodeURIComponent(redirectURI)));

    String preAuthUrlStr = preAuthUrl.toString();

    if (StringUtils.isNotEmpty(authType)) {
      preAuthUrlStr = preAuthUrlStr.replace("&auth_type=xxx", "&auth_type=" + authType);
    } else {
      preAuthUrlStr = preAuthUrlStr.replace("&auth_type=xxx", "");
    }
    if (StringUtils.isNotEmpty(bizAppid)) {
      preAuthUrlStr = preAuthUrlStr.replace("&biz_appid=xxx", "&biz_appid=" + bizAppid);
    } else {
      preAuthUrlStr = preAuthUrlStr.replace("&biz_appid=xxx", "");
    }
    return preAuthUrlStr;
  }

  /**
   * 使用授权码换取公众号或小程序的接口调用凭据和授权信息
   *
   * @param authorizationCode 授权码
   * @return 接口调用凭据和授权信息
   * @throws WxErrorException 微信异常
   */
  public WxOpenQueryAuthResult getQueryAuth(String authorizationCode) throws WxErrorException {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("component_appid", wxOpenProperties.getComponentAppId());
    jsonObject.addProperty("authorization_code", authorizationCode);
    String responseContent = httpPost(API_QUERY_AUTH_URL, jsonObject.toString());

    WxOpenQueryAuthResult queryAuth = WxOpenGsonBuilder
        .create().fromJson(responseContent, WxOpenQueryAuthResult.class);
    if (queryAuth == null || queryAuth.getAuthorizationInfo() == null) {
      return queryAuth;
    }
    WxOpenAuthorizationInfo authorizationInfo = queryAuth.getAuthorizationInfo();
    if (authorizationInfo.getAuthorizerAccessToken() != null) {
      wxOpenCacheService.updateAuthorizerAccessToken(authorizationInfo.getAuthorizerAppid(),
          authorizationInfo.getAuthorizerAccessToken(), authorizationInfo.getExpiresIn());
    }
    if (authorizationInfo.getAuthorizerRefreshToken() != null) {
      wxOpenCacheService.setAuthorizerRefreshToken(authorizationInfo.getAuthorizerAppid(),
          authorizationInfo.getAuthorizerRefreshToken());
    }
    return queryAuth;
  }

  /**
   * 获取（刷新）授权公众号或小程序的接口调用凭据
   */
  public String getAuthorizerAccessToken(String appId, boolean forceRefresh)
      throws WxErrorException {
    if (wxOpenCacheService.isAuthorizerAccessTokenExpired(appId) || forceRefresh) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("component_appid", wxOpenProperties.getComponentAppId());
      jsonObject.addProperty("authorizer_appid", appId);
      jsonObject.addProperty("authorizer_refresh_token",
          wxOpenCacheService.getAuthorizerRefreshToken(appId));

      String responseContent = httpPost(API_AUTHORIZER_TOKEN_URL, jsonObject.toString());

      WxOpenAuthorizerAccessToken wxOpenAuthorizerAccessToken = WxOpenAuthorizerAccessToken
          .fromJson(responseContent);

      wxOpenCacheService.updateAuthorizerAccessToken(appId,
          wxOpenAuthorizerAccessToken.getAuthorizerAccessToken(),
          wxOpenAuthorizerAccessToken.getExpiresIn());
    }
    return wxOpenCacheService.getAuthorizerAccessToken(appId);
  }

  /**
   * 获取授权公众号或小程序基本信息
   * <pre>
   *  该API用于获取授权方的基本信息，包括头像、昵称、帐号类型、认证类型、微信号、原始ID和二维码图片URL
   *  需要特别记录授权方的帐号类型，在消息及事件推送时，对于不具备客服接口的公众号，需要在5秒内立即响应；
   *  而若有客服接口，则可以选择暂时不响应，而选择后续通过客服接口来发送消息触达粉丝。
   * </pre>
   *
   * @param authorizerAppid 公众号或小程序AppId
   * @return 基本信息
   * @throws WxErrorException 微信异常
   */
  public WxOpenAuthorizerInfoResult getAuthorizerInfo(String authorizerAppid)
      throws WxErrorException {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("component_appid", wxOpenProperties.getComponentAppId());
    jsonObject.addProperty("authorizer_appid", authorizerAppid);
    String responseContent = httpPost(API_GET_AUTHORIZER_INFO_URL, jsonObject.toString());
    return WxOpenGsonBuilder.create().fromJson(responseContent, WxOpenAuthorizerInfoResult.class);
  }

  /**
   * 获取授权列表
   */
  public WxOpenAuthorizerListResult getAuthorizerList(int begin, int len) throws WxErrorException {
    begin = begin < 0 ? 0 : begin;
    len = len == 0 ? 10 : len;

    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("component_appid", wxOpenProperties.getComponentAppId());
    jsonObject.addProperty("offset", begin);
    jsonObject.addProperty("count", len);

    String responseContent = httpPost(API_GET_AUTHORIZER_LIST, jsonObject.toString());

    WxOpenAuthorizerListResult ret = WxOpenGsonBuilder.create()
        .fromJson(responseContent, WxOpenAuthorizerListResult.class);
    if (ret != null && ret.getList() != null) {
      for (Map<String, String> data : ret.getList()) {
        String authorizerAppid = data.get("authorizer_appid");
        String authorizerRefreshToken = data.get("refresh_token");
        if (authorizerAppid != null && authorizerRefreshToken != null) {
          wxOpenCacheService.setAuthorizerRefreshToken(authorizerAppid, authorizerRefreshToken);
        }
      }
    }
    return ret;
  }

  /**
   * 获取授权方的选项设置信息
   *
   * @param optionName 选项名
   * @return 选项信息
   */
  public WxOpenAuthorizerOptionResult getAuthorizerOption(String authorizerAppid, String optionName)
      throws WxErrorException {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("component_appid", wxOpenProperties.getComponentAppId());
    jsonObject.addProperty("authorizer_appid", authorizerAppid);
    jsonObject.addProperty("option_name", optionName);
    String responseContent = httpPost(API_GET_AUTHORIZER_OPTION_URL, jsonObject.toString());
    return WxOpenGsonBuilder.create().fromJson(responseContent, WxOpenAuthorizerOptionResult.class);
  }

  /**
   * 设置授权方的选项信息
   */
  public void setAuthorizerOption(String authorizerAppid, String optionName, String optionValue)
      throws WxErrorException {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("component_appid", wxOpenProperties.getComponentAppId());
    jsonObject.addProperty("authorizer_appid", authorizerAppid);
    jsonObject.addProperty("option_name", optionName);
    jsonObject.addProperty("option_value", optionValue);
    httpPost(API_SET_AUTHORIZER_OPTION_URL, jsonObject.toString());
  }

  /**
   * 微信登录
   */
  public WxMaJscode2SessionResult miniappJscode2Session(String appId, String jsCode)
      throws WxErrorException {
    String url = String
        .format(MINIAPP_JSCODE_2_SESSION, appId, jsCode, wxOpenProperties.getComponentAppId());
    String responseContent = httpGet(url);
    return WxMaJscode2SessionResult.fromJson(responseContent);
  }
}
