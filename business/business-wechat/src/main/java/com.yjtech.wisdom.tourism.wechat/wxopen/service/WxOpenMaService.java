package com.yjtech.wisdom.tourism.wechat.wxopen.service;

import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_BIND_TESTER;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_CODE_COMMIT;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_GET_ACCOUNT_BASICINFO;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_GET_AUDIT_STATUS;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_GET_CATEGORY;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_GET_LATEST_AUDIT_STATUS;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_GET_PAGE;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_GET_TESTERLIST;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_GET_WEAPP_SUPPORT_VERSION;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_MODIFY_DOMAIN;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_RELEASE;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_REVERT_CODE_RELEASE;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_SET_WEAPP_SUPPORT_VERSION;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_SET_WEBVIEW_DOMAIN;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_SUBMIT_AUDIT;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_TEST_QRCODE;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_UNBIND_TESTER;
import static com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenApiConstants.API_UNDO_CODE_AUDIT;

import com.yjtech.wisdom.tourism.wechat.wxopen.bean.ma.WxMaOpenCommitExtInfo;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.ma.WxMaPhoneNumberInfo;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.ma.WxMaQrcodeParam;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.ma.WxMaUserInfo;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.message.WxOpenMaSubmitAuditMessage;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxFastMaAccountBasicInfoResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxMaJscode2SessionResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxOpenMaCategoryListResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxOpenMaDomainResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxOpenMaPageListResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxOpenMaQueryAuditResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxOpenMaSubmitAuditResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxOpenMaTesterListResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxOpenResult;
import com.yjtech.wisdom.tourism.wechat.wxcommon.error.WxErrorException;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.crypto.WxMaCryptUtils;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.http.MaQrCodeHttpExecutor;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.json.WxOpenGsonBuilder;
import com.google.gson.JsonObject;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 开放平台代小程序管理服务类 Created by wuyongchong on 2019/9/7.
 */
public class WxOpenMaService extends AbstractWxOpenService {

  private final WxOpenComponentService wxOpenComponentService;

  public WxOpenMaService(
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

  public WxMaJscode2SessionResult jsCode2SessionInfo(String appId, String jsCode)
      throws WxErrorException {
    return wxOpenComponentService.miniappJscode2Session(appId, jsCode);
  }

  public String getAccessToken(String appId, boolean forceRefresh) throws WxErrorException {
    return wxOpenComponentService.getAuthorizerAccessToken(appId, forceRefresh);
  }

  public WxMaUserInfo getUserInfo(String sessionKey, String encryptedData, String ivStr) {
    return WxMaUserInfo.fromJson(WxMaCryptUtils.decrypt(sessionKey, encryptedData, ivStr));
  }

  public WxMaPhoneNumberInfo getPhoneNoInfo(String sessionKey, String encryptedData, String ivStr) {
    return WxMaPhoneNumberInfo.fromJson(WxMaCryptUtils.decrypt(sessionKey, encryptedData, ivStr));
  }

  public boolean checkUserInfo(String sessionKey, String rawData, String signature) {
    final String generatedSignature = DigestUtils.sha1Hex(rawData + sessionKey);
    return generatedSignature.equals(signature);
  }

  /**
   * 获得小程序的域名配置信息
   */
  public WxOpenMaDomainResult getDomain(String appId) throws WxErrorException {
    return modifyDomain(appId, "get", null, null, null, null);
  }

  /**
   * 修改服务器域名
   *
   * @param action add添加, delete删除, set覆盖, get获取。当参数是get时不需要填四个域名字段
   */
  public WxOpenMaDomainResult modifyDomain(String appId, String action,
      List<String> requestdomainList, List<String> wsrequestdomainList,
      List<String> uploaddomainList, List<String> downloaddomainList) throws WxErrorException {
    JsonObject requestJson = new JsonObject();
    requestJson.addProperty("action", action);
    if (!"get".equals(action)) {
      requestJson.add("requestdomain", toJsonArray(requestdomainList));
      requestJson.add("wsrequestdomain", toJsonArray(wsrequestdomainList));
      requestJson.add("uploaddomain", toJsonArray(uploaddomainList));
      requestJson.add("downloaddomain", toJsonArray(downloaddomainList));
    }
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpPost(API_MODIFY_DOMAIN, accessToken, GSON.toJson(requestJson));
    return WxOpenGsonBuilder.create().fromJson(response, WxOpenMaDomainResult.class);
  }


  /**
   * 获取小程序的业务域名
   */
  public String getWebViewDomain(String appId) throws WxErrorException {
    return setWebViewDomain(appId, "get", null);
  }

  /**
   * 设置小程序的业务域名
   *
   * @param action add添加, delete删除, set覆盖, get获取。当参数是get时不需要填webviewdomain字段。如果没有action字段参数，则默认将开放平台第三方登记的小程序业务域名全部添加到授权的小程序中
   */
  public String setWebViewDomain(String appId, String action, List<String> domainList)
      throws WxErrorException {
    JsonObject requestJson = new JsonObject();
    requestJson.addProperty("action", action);
    if (!"get".equals(action)) {
      requestJson.add("webviewdomain", toJsonArray(domainList));
    }
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpPost(API_SET_WEBVIEW_DOMAIN, accessToken, GSON.toJson(requestJson));
    return response;
  }

  /**
   * 获取帐号基本信息
   */
  public WxFastMaAccountBasicInfoResult getAccountBasicInfo(String appId) throws WxErrorException {
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpGet(API_GET_ACCOUNT_BASICINFO, accessToken);
    return WxOpenGsonBuilder.create().fromJson(response, WxFastMaAccountBasicInfoResult.class);
  }

  /**
   * 绑定小程序体验者
   *
   * @param wechatid 体验者微信号（不是openid）
   */
  public WxOpenResult bindTester(String appId, String wechatid) throws WxErrorException {
    JsonObject paramJson = new JsonObject();
    paramJson.addProperty("wechatid", wechatid);
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpPost(API_BIND_TESTER, accessToken, GSON.toJson(paramJson));
    return WxOpenGsonBuilder.create().fromJson(response, WxOpenResult.class);
  }

  /**
   * 解除绑定小程序体验者
   *
   * @param wechatid 体验者微信号（不是openid）
   */
  public WxOpenResult unbindTester(String appId, String wechatid) throws WxErrorException {
    JsonObject paramJson = new JsonObject();
    paramJson.addProperty("wechatid", wechatid);
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpPost(API_UNBIND_TESTER, accessToken, GSON.toJson(paramJson));
    return WxOpenGsonBuilder.create().fromJson(response, WxOpenResult.class);
  }

  /**
   * 获得体验者列表
   */
  public WxOpenMaTesterListResult getTesterList(String appId) throws WxErrorException {
    JsonObject paramJson = new JsonObject();
    paramJson.addProperty("action", "get_experiencer");
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpPost(API_GET_TESTERLIST, accessToken, GSON.toJson(paramJson));
    return WxOpenGsonBuilder.create().fromJson(response, WxOpenMaTesterListResult.class);
  }

  /**
   * 1、为授权的小程序帐号上传小程序代码
   *
   * @param templateId 代码模板ID
   * @param userVersion 用户定义版本
   * @param userDesc 用户定义版本描述
   * @param extInfo 第三方自定义的配置
   */
  public WxOpenResult codeCommit(String appId, Long templateId, String userVersion, String userDesc,
      WxMaOpenCommitExtInfo extInfo) throws WxErrorException {
    JsonObject params = new JsonObject();
    params.addProperty("template_id", templateId);
    params.addProperty("user_version", userVersion);
    params.addProperty("user_desc", userDesc);
    //注意：ext_json必须是字符串类型
    params.addProperty("ext_json", GSON.toJson(extInfo));
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpPost(API_CODE_COMMIT, accessToken, GSON.toJson(params));
    return WxOpenGsonBuilder.create().fromJson(response, WxOpenResult.class);
  }

  /**
   * 获取体验小程序的体验二维码
   */
  public File getTestQrcode(String appId, String pagePath, Map<String, String> params)
      throws WxErrorException {
    WxMaQrcodeParam qrcodeParam = WxMaQrcodeParam.create(pagePath);
    qrcodeParam.addPageParam(params);
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    return MaQrCodeHttpExecutor.getQrCode(API_TEST_QRCODE, accessToken, qrcodeParam);
  }

  /**
   * 获取授权小程序帐号的可选类目
   * <p>
   * 注意：该接口可获取已设置的二级类目及用于代码审核的可选三级类目。
   * </p>
   *
   * @return WxOpenMaCategoryListResult
   */
  public WxOpenMaCategoryListResult getCategoryList(String appId) throws WxErrorException {
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpGet(API_GET_CATEGORY, accessToken);
    return WxOpenGsonBuilder.create().fromJson(response, WxOpenMaCategoryListResult.class);
  }

  /**
   * 获取小程序的第三方提交代码的页面配置（仅供第三方开发者代小程序调用）
   */
  public WxOpenMaPageListResult getPageList(String appId) throws WxErrorException {
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpGet(API_GET_PAGE, accessToken);
    return WxOpenGsonBuilder.create().fromJson(response, WxOpenMaPageListResult.class);
  }

  /**
   * 将第三方提交的代码包提交审核（仅供第三方开发者代小程序调用）
   */
  public WxOpenMaSubmitAuditResult submitAudit(String appId,
      WxOpenMaSubmitAuditMessage submitAuditMessage) throws WxErrorException {
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpPost(API_SUBMIT_AUDIT, accessToken, GSON.toJson(submitAuditMessage));
    return WxOpenGsonBuilder.create().fromJson(response, WxOpenMaSubmitAuditResult.class);
  }

  /**
   * 7. 查询某个指定版本的审核状态（仅供第三方代小程序调用）
   */
  public WxOpenMaQueryAuditResult getAuditStatus(String appId, Long auditid)
      throws WxErrorException {
    JsonObject params = new JsonObject();
    params.addProperty("auditid", auditid);
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpPost(API_GET_AUDIT_STATUS, accessToken, GSON.toJson(params));
    return WxOpenGsonBuilder.create().fromJson(response, WxOpenMaQueryAuditResult.class);
  }

  /**
   * 8. 查询最新一次提交的审核状态（仅供第三方代小程序调用）
   */
  public WxOpenMaQueryAuditResult getLatestAuditStatus(String appId) throws WxErrorException {
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpGet(API_GET_LATEST_AUDIT_STATUS, accessToken);
    return WxOpenGsonBuilder.create().fromJson(response, WxOpenMaQueryAuditResult.class);
  }

  /**
   * 9. 发布已通过审核的小程序（仅供第三方代小程序调用）
   * <p>
   * 请填写空的数据包，POST的json数据包为空即可。
   * </p>
   */
  public WxOpenResult releaesAudited(String appId) throws WxErrorException {
    JsonObject params = new JsonObject();
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpPost(API_RELEASE, accessToken, GSON.toJson(params));
    return WxOpenGsonBuilder.create().fromJson(response, WxOpenResult.class);
  }

  /**
   * 11. 小程序版本回退（仅供第三方代小程序调用）
   */
  public WxOpenResult revertCodeReleaes(String appId) throws WxErrorException {
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpGet(API_REVERT_CODE_RELEASE, accessToken);
    return WxOpenGsonBuilder.create().fromJson(response, WxOpenResult.class);
  }


  /**
   * 12. 查询当前设置的最低基础库版本及各版本用户占比 （仅供第三方代小程序调用）
   */
  public String getSupportVersion(String appId) throws WxErrorException {
    JsonObject params = new JsonObject();
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpPost(API_GET_WEAPP_SUPPORT_VERSION, accessToken, GSON.toJson(params));
    return response;
  }

  /**
   * 13. 设置最低基础库版本（仅供第三方代小程序调用）
   */
  public WxOpenResult setWeappSupportVersion(String appId, String version) throws WxErrorException {
    JsonObject params = new JsonObject();
    params.addProperty("version", version);
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpPost(API_SET_WEAPP_SUPPORT_VERSION, accessToken, GSON.toJson(params));
    return WxOpenGsonBuilder.create().fromJson(response, WxOpenResult.class);
  }

  /**
   * 15. 小程序审核撤回
   * <p>
   * 单个帐号每天审核撤回次数最多不超过1次，一个月不超过10次。
   * </p>
   */
  public WxOpenResult undoCodeAudit(String appId) throws WxErrorException {
    String accessToken = wxOpenComponentService.getAuthorizerAccessToken(appId, false);
    String response = httpGet(API_UNDO_CODE_AUDIT, accessToken);
    return WxOpenGsonBuilder.create().fromJson(response, WxOpenResult.class);
  }

}
