package com.yjtech.wisdom.tourism.wechat.wxopen.service;

import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.OPEN_ADD_CATEGORY;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.OPEN_API_WXA_QUERYNICKNAME;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.OPEN_CHECK_WX_VERIFY_NICKNAME;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.OPEN_COMPONENT_REBIND_ADMIN;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.OPEN_DELETE_CATEGORY;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.OPEN_GET_ACCOUNT_BASIC_INFO;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.OPEN_GET_ALL_CATEGORIES;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.OPEN_GET_CATEGORY;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.OPEN_MODIFY_CATEGORY;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.OPEN_MODIFY_HEADIMAGE;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.OPEN_MODIFY_SIGNATURE;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.OPEN_SET_NICKNAME;

import com.yjtech.wisdom.tourism.wechat.wxopen.bean.fastma.WxFastMaCategory;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxFastMaAccountBasicInfoResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxFastMaBeenSetCategoryResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxFastMaCheckNickameResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxFastMaQueryNicknameStatusResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxFastMaSetNickameResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxOpenResult;
import com.yjtech.wisdom.tourism.wechat.wxcommon.error.WxErrorException;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.json.WxOpenGsonBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信开放平台【快速创建小程序】的专用接口 Created by wuyongchong on 2019/9/7.
 */
public class WxOpenFastMaService extends AbstractWxOpenService {

  private final WxOpenComponentService wxOpenComponentService;

  protected static final Gson GSON = new Gson();

  public WxOpenFastMaService(
      WxOpenComponentService wxOpenComponentService) {
    this.wxOpenComponentService = wxOpenComponentService;
  }

  private String httpGet(String url, String authorizerAccessToken) throws WxErrorException {
    String uriWithAccessToken =
        url + (url.contains("?") ? "&" : "?") + "access_token="
            + authorizerAccessToken;
    return doHttpGet(uriWithAccessToken);
  }

  private String httpPost(String url, String authorizerAccessToken, String postData)
      throws WxErrorException {
    String uriWithAccessToken =
        url + (url.contains("?") ? "&" : "?") + "access_token="
            + authorizerAccessToken;
    return doHttpPost(uriWithAccessToken, postData);
  }


  /**
   * 获取帐号基本信息
   */
  public WxFastMaAccountBasicInfoResult getAccountBasicInfo(String appId) throws WxErrorException {
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpGet(OPEN_GET_ACCOUNT_BASIC_INFO, accessToken);
    return WxOpenGsonBuilder.create().fromJson(response, WxFastMaAccountBasicInfoResult.class);
  }

  /**
   * 2.小程序名称设置及改名
   *
   * @param nickname 昵称
   * @param idCard 身份证照片–临时素材mediaid(个人号必填)
   * @param license 组织机构代码证或营业执照–临时素材mediaid(组织号必填)
   * @param namingOtherStuff1 其他证明材料---临时素材 mediaid
   * @param namingOtherStuff2 其他证明材料---临时素材 mediaid
   */
  public WxFastMaSetNickameResult setNickname(String appId, String nickname, String idCard,
      String license,
      String namingOtherStuff1, String namingOtherStuff2) throws WxErrorException {
    JsonObject params = new JsonObject();
    params.addProperty("nick_name", nickname);
    params.addProperty("id_card", idCard);
    params.addProperty("license", license);
    params.addProperty("naming_other_stuff_1", namingOtherStuff1);
    params.addProperty("naming_other_stuff_2", namingOtherStuff2);
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpPost(OPEN_SET_NICKNAME, accessToken, params.toString());
    return WxOpenGsonBuilder.create().fromJson(response, WxFastMaSetNickameResult.class);
  }

  /**
   * 3 小程序改名审核状态查询
   *
   * @param auditId 审核单id
   */
  public WxFastMaQueryNicknameStatusResult querySetNicknameStatus(String appId, String auditId)
      throws WxErrorException {
    JsonObject params = new JsonObject();
    params.addProperty("audit_id", auditId);
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpPost(OPEN_API_WXA_QUERYNICKNAME, accessToken, params.toString());
    return WxOpenGsonBuilder.create().fromJson(response, WxFastMaQueryNicknameStatusResult.class);
  }

  /**
   * 4. 微信认证名称检测
   * <pre>
   *      命中关键字策略时返回命中关键字的说明描述
   *  </pre>
   *
   * @param nickname 名称
   */
  public WxFastMaCheckNickameResult checkWxVerifyNickname(String appId, String nickname)
      throws WxErrorException {
    JsonObject params = new JsonObject();
    params.addProperty("nick_name", nickname);
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpPost(OPEN_CHECK_WX_VERIFY_NICKNAME, accessToken, params.toString());
    return WxOpenGsonBuilder.create().fromJson(response, WxFastMaCheckNickameResult.class);
  }

  /**
   * 5.修改头像
   * <pre>
   *     图片格式只支持：BMP、JPEG、JPG、GIF、PNG，大小不超过2M
   *      注：实际头像始终为正方形
   * </pre>
   *
   * @param headImgMediaId 头像素材media_id
   * @param x1 裁剪框左上角x坐标（取值范围：[0, 1]）
   * @param y1 裁剪框左上角y坐标（取值范围：[0, 1]）
   * @param x2 裁剪框右下角x坐标（取值范围：[0, 1]）
   * @param y2 裁剪框右下角y坐标（取值范围：[0, 1]）
   */
  public WxOpenResult modifyHeadImage(String appId, String headImgMediaId, float x1, float y1,
      float x2, float y2) throws WxErrorException {
    JsonObject params = new JsonObject();
    params.addProperty("head_img_media_id", headImgMediaId);
    params.addProperty("x1", x1);
    params.addProperty("y1", y1);
    params.addProperty("x2", x2);
    params.addProperty("y2", y2);
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpPost(OPEN_MODIFY_HEADIMAGE, accessToken, params.toString());
    return WxOpenGsonBuilder.create().fromJson(response, WxOpenResult.class);
  }

  /**
   * 6.修改功能介绍
   *
   * @param signature 简介：4-120字
   */
  public WxOpenResult modifySignature(String appId, String signature) throws WxErrorException {
    JsonObject params = new JsonObject();
    params.addProperty("signature", signature);
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpPost(OPEN_MODIFY_SIGNATURE, accessToken, params.toString());
    return WxOpenGsonBuilder.create().fromJson(response, WxOpenResult.class);
  }

  /**
   * 7.3 管理员换绑
   *
   * @param taskid 换绑管理员任务序列号(公众平台最终点击提交回跳到第三方平台时携带)
   */
  public WxOpenResult componentRebindAdmin(String appId, String taskid) throws WxErrorException {
    JsonObject params = new JsonObject();
    params.addProperty("taskid", taskid);
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpPost(OPEN_COMPONENT_REBIND_ADMIN, accessToken, params.toString());
    return WxOpenGsonBuilder.create().fromJson(response, WxOpenResult.class);
  }

  /**
   * 8.1 获取账号可以设置的所有类目
   */
  public String getAllCategories(String appId) throws WxErrorException {
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    return httpGet(OPEN_GET_ALL_CATEGORIES, accessToken);
  }

  /**
   * 8.2 添加类目
   */
  public WxOpenResult addCategory(String appId, List<WxFastMaCategory> categoryList)
      throws WxErrorException {
    Map<String, Object> map = new HashMap<>();
    map.put("categories", categoryList);
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpPost(OPEN_ADD_CATEGORY, accessToken,
        WxOpenGsonBuilder.create().toJson(map));
    return WxOpenGsonBuilder.create().fromJson(response, WxOpenResult.class);
  }

  /**
   * 8.3删除类目
   *
   * @param first 一级类目ID
   * @param second 二级类目ID
   */
  public WxOpenResult deleteCategory(String appId, int first, int second) throws WxErrorException {
    JsonObject params = new JsonObject();
    params.addProperty("first", first);
    params.addProperty("second", second);

    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpPost(OPEN_DELETE_CATEGORY, accessToken, GSON.toJson(params));

    return WxOpenGsonBuilder.create().fromJson(response, WxOpenResult.class);
  }

  /**
   * 8.4获取账号已经设置的所有类目
   */
  public WxFastMaBeenSetCategoryResult getCategory(String appId) throws WxErrorException {
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpGet(OPEN_GET_CATEGORY, accessToken);
    return WxOpenGsonBuilder.create().fromJson(response, WxFastMaBeenSetCategoryResult.class);
  }

  /**
   * 8.5修改类目
   *
   * @param category 实体
   */
  public WxOpenResult modifyCategory(String appId, WxFastMaCategory category)
      throws WxErrorException {
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpPost(OPEN_MODIFY_CATEGORY, accessToken, GSON.toJson(category));
    return WxOpenGsonBuilder.create().fromJson(response, WxOpenResult.class);
  }

}
