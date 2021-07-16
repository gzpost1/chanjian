package com.yjtech.wisdom.tourism.wechat.wxopen.service;

import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.MESSAGE_CUSTOM_SEND;

import com.yjtech.wisdom.tourism.wechat.wxcommon.error.WxErrorException;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.message.WxMaKefuMessage;

/**
 * Created by wuyongchong on 2019/9/8.
 */
public class WxOpenKefuService extends AbstractWxOpenService {

  private final WxOpenComponentService wxOpenComponentService;

  public WxOpenKefuService(
      WxOpenComponentService wxOpenComponentService) {
    this.wxOpenComponentService = wxOpenComponentService;
  }

  private String httpPost(String url, String authorizerAccessToken, String postData)
      throws WxErrorException {
    return httpPost(url, "access_token", authorizerAccessToken, postData);
  }

  private String httpPost(String url, String accessTokenKey, String accessTokenVal, String postData)
      throws WxErrorException {
    String uriWithAccessToken =
        url + (url.contains("?") ? "&" : "?") + accessTokenKey + "="
            + accessTokenVal;
    return doHttpPost(uriWithAccessToken, postData);
  }

  public boolean sendKefuMessage(WxMaKefuMessage message, String authorizerAccessToken)
      throws WxErrorException {
    String responseContent = httpPost(MESSAGE_CUSTOM_SEND, authorizerAccessToken, message.toJson());
    return responseContent != null;
  }

}
