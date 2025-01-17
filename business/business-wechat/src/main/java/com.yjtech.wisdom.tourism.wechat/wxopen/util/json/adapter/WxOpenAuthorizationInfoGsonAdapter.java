package com.yjtech.wisdom.tourism.wechat.wxopen.util.json.adapter;

import com.yjtech.wisdom.tourism.wechat.wxopen.bean.auth.WxOpenAuthorizationInfo;
import com.yjtech.wisdom.tourism.wechat.wxcommon.util.json.GsonHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
public class WxOpenAuthorizationInfoGsonAdapter implements
    JsonDeserializer<WxOpenAuthorizationInfo> {

  @Override
  public WxOpenAuthorizationInfo deserialize(JsonElement jsonElement, Type type,
      JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    WxOpenAuthorizationInfo authorizationInfo = new WxOpenAuthorizationInfo();
    JsonObject jsonObject = jsonElement.getAsJsonObject();
    authorizationInfo.setAuthorizerAppid(GsonHelper.getString(jsonObject, "authorizer_appid"));
    authorizationInfo
        .setAuthorizerAccessToken(GsonHelper.getString(jsonObject, "authorizer_access_token"));
    authorizationInfo.setExpiresIn(GsonHelper.getPrimitiveInteger(jsonObject, "expires_in"));
    authorizationInfo
        .setAuthorizerRefreshToken(GsonHelper.getString(jsonObject, "authorizer_refresh_token"));
    List<Integer> funcInfo = new ArrayList<>();
    JsonArray jsonArray = GsonHelper.getAsJsonArray(jsonObject.get("func_info"));
    if (jsonArray != null && !jsonArray.isJsonNull()) {
      for (int i = 0; i < jsonArray.size(); i++) {
        jsonObject = jsonArray.get(i).getAsJsonObject();
        if (jsonObject == null || jsonObject.isJsonNull()) {
          continue;
        }
        jsonObject = jsonObject.getAsJsonObject("funcscope_category");
        if (jsonObject == null || jsonObject.isJsonNull()) {
          continue;
        }
        Integer id = GsonHelper.getInteger(jsonObject, "id");
        if (id == null) {
          continue;
        }
        funcInfo.add(id);
      }
    }
    authorizationInfo.setFuncInfo(funcInfo);
    return authorizationInfo;
  }
}
