package com.yjtech.wisdom.tourism.wechat.wxopen.util.json.adapter;

import com.yjtech.wisdom.tourism.wechat.wxopen.bean.WxOpenComponentAccessToken;
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
public class WxOpenComponentAccessTokenGsonAdapter implements
    JsonDeserializer<WxOpenComponentAccessToken> {

  @Override
  public WxOpenComponentAccessToken deserialize(JsonElement jsonElement, Type type,
      JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    WxOpenComponentAccessToken componentAccessToken = new WxOpenComponentAccessToken();
    JsonObject jsonObject = jsonElement.getAsJsonObject();
    componentAccessToken
        .setComponentAccessToken(GsonHelper.getString(jsonObject, "component_access_token"));
    componentAccessToken.setExpiresIn(GsonHelper.getPrimitiveInteger(jsonObject, "expires_in"));
    return componentAccessToken;
  }
}
