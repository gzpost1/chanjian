package com.yjtech.wisdom.tourism.wechat.wxopen.util.crypto;

import com.yjtech.wisdom.tourism.wechat.wxopen.config.WxOpenProperties;
import org.apache.commons.codec.binary.Base64;

/**
 * Created by wuyongchong on 2019/9/5.
 */
public class WxOpenCryptUtil extends WxCryptUtil {

  /**
   * 构造函数
   */
  public WxOpenCryptUtil(WxOpenProperties wxOpenProperties) {
    /*
     * @param token          公众平台上，开发者设置的token
     * @param encodingAesKey 公众平台上，开发者设置的EncodingAESKey
     * @param appId          公众平台appid
     */
    String encodingAesKey = wxOpenProperties.getComponentAesKey();
    String token = wxOpenProperties.getComponentToken();
    String appId = wxOpenProperties.getComponentAppId();

    this.token = token;
    this.appidOrCorpid = appId;
    this.aesKey = Base64.decodeBase64(encodingAesKey + "=");
  }
}
