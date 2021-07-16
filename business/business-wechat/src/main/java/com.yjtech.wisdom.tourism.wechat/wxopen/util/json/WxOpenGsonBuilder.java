package com.yjtech.wisdom.tourism.wechat.wxopen.util.json;

import com.yjtech.wisdom.tourism.wechat.wxopen.bean.WxOpenAuthorizerAccessToken;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.WxOpenComponentAccessToken;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.auth.WxOpenAuthorizationInfo;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.auth.WxOpenAuthorizerInfo;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxFastMaAccountBasicInfoResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxOpenAuthorizerInfoResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxOpenAuthorizerOptionResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxOpenQueryAuthResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.json.adapter.WxFastMaAccountBasicInfoGsonAdapter;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.json.adapter.WxOpenAuthorizationInfoGsonAdapter;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.json.adapter.WxOpenAuthorizerAccessTokenGsonAdapter;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.json.adapter.WxOpenAuthorizerInfoGsonAdapter;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.json.adapter.WxOpenAuthorizerInfoResultGsonAdapter;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.json.adapter.WxOpenAuthorizerOptionResultGsonAdapter;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.json.adapter.WxOpenComponentAccessTokenGsonAdapter;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.json.adapter.WxOpenQueryAuthResultGsonAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
public class WxOpenGsonBuilder {

  private static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();

    INSTANCE.registerTypeAdapter(WxOpenComponentAccessToken.class,
        new WxOpenComponentAccessTokenGsonAdapter());

    INSTANCE.registerTypeAdapter(WxOpenAuthorizerAccessToken.class,
        new WxOpenAuthorizerAccessTokenGsonAdapter());

    INSTANCE.registerTypeAdapter(WxOpenAuthorizationInfo.class,
        new WxOpenAuthorizationInfoGsonAdapter());

    INSTANCE.registerTypeAdapter(WxOpenAuthorizerInfo.class, new WxOpenAuthorizerInfoGsonAdapter());

    INSTANCE.registerTypeAdapter(WxOpenQueryAuthResult.class, new WxOpenQueryAuthResultGsonAdapter());

    INSTANCE.registerTypeAdapter(WxOpenAuthorizerInfoResult.class,
        new WxOpenAuthorizerInfoResultGsonAdapter());

    INSTANCE.registerTypeAdapter(WxOpenAuthorizerOptionResult.class,
        new WxOpenAuthorizerOptionResultGsonAdapter());

    INSTANCE.registerTypeAdapter(WxFastMaAccountBasicInfoResult.class,
        new WxFastMaAccountBasicInfoGsonAdapter());

  }

  public static Gson create() {
    return INSTANCE.create();
  }

}
