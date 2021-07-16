package com.yjtech.wisdom.tourism.wechat.wxopen.util.json.adapter;

import com.yjtech.wisdom.tourism.wechat.wxopen.bean.WxOpenAuthorizerAccessToken;
import com.yjtech.wisdom.tourism.wechat.wxcommon.util.json.GsonHelper;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
public class WxOpenAuthorizerAccessTokenGsonAdapter implements
    JsonDeserializer<WxOpenAuthorizerAccessToken> {

  @Override
  public WxOpenAuthorizerAccessToken deserialize(JsonElement jsonElement, Type type,
      JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    WxOpenAuthorizerAccessToken authorizerAccessToken = new WxOpenAuthorizerAccessToken();
    JsonObject jsonObject = jsonElement.getAsJsonObject();
    authorizerAccessToken
        .setAuthorizerAccessToken(GsonHelper.getString(jsonObject, "authorizer_access_token"));
    authorizerAccessToken.setExpiresIn(GsonHelper.getPrimitiveInteger(jsonObject, "expires_in"));
    return authorizerAccessToken;
  }
}
