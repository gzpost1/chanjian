package com.yjtech.wisdom.tourism.wechat.wxopen.util.json.adapter;

import com.yjtech.wisdom.tourism.wechat.wxopen.bean.auth.WxOpenAuthorizationInfo;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.auth.WxOpenAuthorizerInfo;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxOpenAuthorizerInfoResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.json.WxOpenGsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
public class WxOpenAuthorizerInfoResultGsonAdapter implements
    JsonDeserializer<WxOpenAuthorizerInfoResult> {

  @Override
  public WxOpenAuthorizerInfoResult deserialize(JsonElement jsonElement, Type type,
      JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    WxOpenAuthorizerInfoResult authorizerInfoResult = new WxOpenAuthorizerInfoResult();
    JsonObject jsonObject = jsonElement.getAsJsonObject();

    WxOpenAuthorizationInfo authorizationInfo = WxOpenGsonBuilder.create()
        .fromJson(jsonObject.get("authorization_info"),
            new TypeToken<WxOpenAuthorizationInfo>() {
            }.getType());

    authorizerInfoResult.setAuthorizationInfo(authorizationInfo);
    WxOpenAuthorizerInfo authorizerInfo = WxOpenGsonBuilder.create()
        .fromJson(jsonObject.get("authorizer_info"),
            new TypeToken<WxOpenAuthorizerInfo>() {
            }.getType());

    authorizerInfoResult.setAuthorizerInfo(authorizerInfo);
    return authorizerInfoResult;
  }
}
