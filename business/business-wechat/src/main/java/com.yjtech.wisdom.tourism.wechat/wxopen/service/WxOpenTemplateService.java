package com.yjtech.wisdom.tourism.wechat.wxopen.service;

import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.ADD_TO_TEMPLATE_URL;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.DELETE_TEMPLATE_URL;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.GET_TEMPLATE_DRAFT_LIST_URL;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.GET_TEMPLATE_LIST_URL;

import com.yjtech.wisdom.tourism.wechat.wxopen.bean.WxOpenMaCodeTemplate;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxOpenResult;
import com.yjtech.wisdom.tourism.wechat.wxcommon.error.WxErrorException;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.json.WxOpenGsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by wuyongchong on 2019/9/8.
 */
public class WxOpenTemplateService extends AbstractWxOpenService {

  private final WxOpenComponentService wxOpenComponentService;

  public WxOpenTemplateService(
      WxOpenComponentService wxOpenComponentService) {
    this.wxOpenComponentService = wxOpenComponentService;
  }

  private String httpGet(String url, String accessTokenKey, String accessTokenVal)
      throws WxErrorException {
    String uriWithAccessToken =
        url + (url.contains("?") ? "&" : "?") + accessTokenKey + "="
            + accessTokenVal;
    return doHttpGet(uriWithAccessToken);
  }

  private String httpPost(String url, String accessTokenKey, String accessTokenVal, String postData)
      throws WxErrorException {
    String uriWithAccessToken =
        url + (url.contains("?") ? "&" : "?") + accessTokenKey + "="
            + accessTokenVal;
    return doHttpPost(uriWithAccessToken, postData);
  }

  /**
   * 1.获取草稿箱内的所有临时代码草稿
   */
  public List<WxOpenMaCodeTemplate> getTemplateDraftList() throws WxErrorException {

    String componentAccessToken = wxOpenComponentService.getComponentAccessToken(false);

    String responseContent = httpGet(GET_TEMPLATE_DRAFT_LIST_URL, "access_token",
        componentAccessToken);

    JsonObject response = JSON_PARSER.parse(StringUtils.defaultString(responseContent, "{}"))
        .getAsJsonObject();

    boolean hasDraftList = response.has("draft_list");
    if (hasDraftList) {
      return WxOpenGsonBuilder.create().fromJson(response.getAsJsonArray("draft_list"),
          new TypeToken<List<WxOpenMaCodeTemplate>>() {
          }.getType());
    } else {
      return null;
    }
  }

  /**
   * 获取代码模版库中的所有小程序代码模版
   */
  public List<WxOpenMaCodeTemplate> getTemplateList() throws WxErrorException {
    String componentAccessToken = wxOpenComponentService.getComponentAccessToken(false);
    String responseContent = httpGet(GET_TEMPLATE_LIST_URL, "access_token",
        componentAccessToken);
    JsonObject response = JSON_PARSER.parse(StringUtils.defaultString(responseContent, "{}"))
        .getAsJsonObject();
    boolean hasDraftList = response.has("template_list");
    if (hasDraftList) {
      return WxOpenGsonBuilder.create().fromJson(response.getAsJsonArray("template_list"),
          new TypeToken<List<WxOpenMaCodeTemplate>>() {
          }.getType());
    } else {
      return null;
    }
  }

  /**
   * 将草稿箱的草稿选为小程序代码模版
   */
  public WxOpenResult addToTemplate(long draftId) throws WxErrorException {
    JsonObject param = new JsonObject();
    param.addProperty("draft_id", draftId);
    String componentAccessToken = wxOpenComponentService.getComponentAccessToken(false);
    String response = httpPost(ADD_TO_TEMPLATE_URL, "access_token", componentAccessToken,
        param.toString());
    return WxOpenGsonBuilder.create().fromJson(response, WxOpenResult.class);
  }

  /**
   * 删除指定小程序代码模版
   */
  public WxOpenResult deleteTemplate(long templateId) throws WxErrorException {
    JsonObject param = new JsonObject();
    param.addProperty("template_id", templateId);
    String componentAccessToken = wxOpenComponentService.getComponentAccessToken(false);
    String response = httpPost(DELETE_TEMPLATE_URL, "access_token", componentAccessToken,
        param.toString());
    return WxOpenGsonBuilder.create().fromJson(response, WxOpenResult.class);
  }

}
