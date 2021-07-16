package com.yjtech.wisdom.tourism.wechat.wxopen.util.json.adapter;

import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxOpenAuthorizerOptionResult;
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
public class WxOpenAuthorizerOptionResultGsonAdapter implements
    JsonDeserializer<WxOpenAuthorizerOptionResult> {

  @Override
  public WxOpenAuthorizerOptionResult deserialize(JsonElement jsonElement, Type type,
      JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    WxOpenAuthorizerOptionResult authorizerOptionResult = new WxOpenAuthorizerOptionResult();
    JsonObject jsonObject = jsonElement.getAsJsonObject();
    authorizerOptionResult.setAuthorizerAppid(GsonHelper.getString(jsonObject, "authorizer_appid"));
    authorizerOptionResult.setOptionName(GsonHelper.getString(jsonObject, "option_name"));
    authorizerOptionResult.setOptionValue(GsonHelper.getString(jsonObject, "option_value"));
    return authorizerOptionResult;
  }
}
