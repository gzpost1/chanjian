package com.yjtech.wisdom.tourism.wechat.wxopen;

/**
 * Created by wuyongchong on 2019/9/6.
 */
public final class WxOpenApiConstants {

  public final static String API_COMPONENT_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";
  public final static String API_CREATE_PREAUTHCODE_URL = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode";
  public final static String API_QUERY_AUTH_URL = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth";
  public final static String API_AUTHORIZER_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token";
  public final static String API_GET_AUTHORIZER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info";
  public final static String API_GET_AUTHORIZER_OPTION_URL = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_option";
  public final static String API_SET_AUTHORIZER_OPTION_URL = "https://api.weixin.qq.com/cgi-bin/component/api_set_authorizer_option";
  public final static String API_GET_AUTHORIZER_LIST = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_list";

  public final static String COMPONENT_LOGIN_PAGE_URL = "https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid=%s&pre_auth_code=%s&redirect_uri=%s&auth_type=xxx&biz_appid=xxx";

  /**
   * 手机端打开授权链接
   */
  public final static String COMPONENT_MOBILE_LOGIN_PAGE_URL = "https://mp.weixin.qq.com/safe/bindcomponent?action=bindcomponent&no_scan=1&auth_type=3&component_appid=%s&pre_auth_code=%s&redirect_uri=%s&auth_type=xxx&biz_appid=xxx#wechat_redirect";
  public final static String CONNECT_OAUTH2_AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s&component_appid=%s#wechat_redirect";

  /**
   * 用code换取oauth2的access token
   */
  public final static String OAUTH2_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/component/access_token?appid=%s&code=%s&grant_type=authorization_code&component_appid=%s";
  /**
   * 刷新oauth2的access token
   */
  public final static String OAUTH2_REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/component/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s&component_appid=%s";

  public final static String MINIAPP_JSCODE_2_SESSION = "https://api.weixin.qq.com/sns/component/jscode2session?appid=%s&js_code=%s&grant_type=authorization_code&component_appid=%s";

  public final static String CREATE_OPEN_URL = "https://api.weixin.qq.com/cgi-bin/open/create";

  /**
   * 快速创建小程序接口
   */
  public final static String FAST_REGISTER_WEAPP_URL = "https://api.weixin.qq.com/cgi-bin/component/fastregisterweapp?action=create";
  public final static String FAST_REGISTER_WEAPP_SEARCH_URL = "https://api.weixin.qq.com/cgi-bin/component/fastregisterweapp?action=search";

  /**
   * 客服接口
   */
  public final static String MESSAGE_CUSTOM_SEND = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
  public final static String GET_KF_LIST = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist";
  public final static String GET_ONLINE_KF_LIST = "https://api.weixin.qq.com/cgi-bin/customservice/getonlinekflist";
  public final static String KFACCOUNT_ADD = "https://api.weixin.qq.com/customservice/kfaccount/add";
  public final static String KFACCOUNT_UPDATE = "https://api.weixin.qq.com/customservice/kfaccount/update";
  public final static String KFACCOUNT_INVITE_WORKER = "https://api.weixin.qq.com/customservice/kfaccount/inviteworker";
  public final static String KFACCOUNT_UPLOAD_HEAD_IMG = "https://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?kf_account=%s";
  public final static String KFACCOUNT_DEL = "https://api.weixin.qq.com/customservice/kfaccount/del?kf_account=%s";
  public final static String KFSESSION_CREATE = "https://api.weixin.qq.com/customservice/kfsession/create";
  public final static String KFSESSION_CLOSE = "https://api.weixin.qq.com/customservice/kfsession/close";
  public final static String KFSESSION_GET_SESSION = "https://api.weixin.qq.com/customservice/kfsession/getsession?openid=%s";
  public final static String KFSESSION_GET_SESSION_LIST = "https://api.weixin.qq.com/customservice/kfsession/getsessionlist?kf_account=%s";
  public final static String KFSESSION_GET_WAIT_CASE = "https://api.weixin.qq.com/customservice/kfsession/getwaitcase";
  public final static String MSG_RECORD_LIST = "https://api.weixin.qq.com/customservice/msgrecord/getmsglist";
  public final static String CUSTOM_TYPING = "https://api.weixin.qq.com/cgi-bin/message/custom/typing";

  /**
   * 1 获取帐号基本信息
   */
  public final static String OPEN_GET_ACCOUNT_BASIC_INFO = "https://api.weixin.qq.com/cgi-bin/account/getaccountbasicinfo";

  /**
   * 2 小程序名称设置及改名
   */
  public final static String OPEN_SET_NICKNAME = "https://api.weixin.qq.com/wxa/setnickname";

  /**
   * 3 小程序改名审核状态查询
   */
  public final static String OPEN_API_WXA_QUERYNICKNAME = "https://api.weixin.qq.com/wxa/api_wxa_querynickname";

  /**
   * 4 微信认证名称检测
   */
  public final static String OPEN_CHECK_WX_VERIFY_NICKNAME = "https://api.weixin.qq.com/cgi-bin/wxverify/checkwxverifynickname";

  /**
   * 5 修改头像
   */
  public final static String OPEN_MODIFY_HEADIMAGE = "https://api.weixin.qq.com/cgi-bin/account/modifyheadimage";

  /**
   * 6修改功能介绍
   */
  public final static String OPEN_MODIFY_SIGNATURE = "https://api.weixin.qq.com/cgi-bin/account/modifysignature";

  /**
   * 7 换绑小程序管理员接口
   */
  public final static String OPEN_COMPONENT_REBIND_ADMIN = "https://api.weixin.qq.com/cgi-bin/account/componentrebindadmin";

  /**
   * 8.1 获取账号可以设置的所有类目
   */
  public final static String OPEN_GET_ALL_CATEGORIES = "https://api.weixin.qq.com/cgi-bin/wxopen/getallcategories";
  /**
   * 8.2 添加类目
   */
  public final static String OPEN_ADD_CATEGORY = "https://api.weixin.qq.com/cgi-bin/wxopen/addcategory";
  /**
   * 8.3 删除类目
   */
  public final static String OPEN_DELETE_CATEGORY = "https://api.weixin.qq.com/cgi-bin/wxopen/deletecategory";
  /**
   * 8.4 获取账号已经设置的所有类目
   */
  public final static String OPEN_GET_CATEGORY = "https://api.weixin.qq.com/cgi-bin/wxopen/getcategory";
  /**
   * 8.5 修改类目
   */
  public final static String OPEN_MODIFY_CATEGORY = "https://api.weixin.qq.com/cgi-bin/wxopen/modifycategory";


  /**
   * 模板管理
   */
  public final static String GET_TEMPLATE_DRAFT_LIST_URL = "https://api.weixin.qq.com/wxa/gettemplatedraftlist";
  public final static String GET_TEMPLATE_LIST_URL = "https://api.weixin.qq.com/wxa/gettemplatelist";
  public final static String ADD_TO_TEMPLATE_URL = "https://api.weixin.qq.com/wxa/addtotemplate";
  public final static String DELETE_TEMPLATE_URL = "https://api.weixin.qq.com/wxa/deletetemplate";


  /**
   * 设置小程序服务器域名.
   *
   * <pre>
   *     授权给第三方的小程序，其服务器域名只可以为第三方的服务器，当小程序通过第三方发布代码上线后，小程序原先自己配置的服务器域名将被删除，
   *     只保留第三方平台的域名，所以第三方平台在代替小程序发布代码之前，需要调用接口为小程序添加第三方自身的域名。
   *     提示：需要先将域名登记到第三方平台的小程序服务器域名中，才可以调用接口进行配置
   * </pre>
   */
  public final static String API_MODIFY_DOMAIN = "https://api.weixin.qq.com/wxa/modify_domain";

  /**
   * 设置小程序业务域名（仅供第三方代小程序调用）
   * <pre>
   *     授权给第三方的小程序，其业务域名只可以为第三方的服务器，当小程序通过第三方发布代码上线后，小程序原先自己配置的业务域名将被删除，
   *     只保留第三方平台的域名，所以第三方平台在代替小程序发布代码之前，需要调用接口为小程序添加业务域名。
   * 提示：
   * 1、需要先将域名登记到第三方平台的小程序业务域名中，才可以调用接口进行配置。
   * 2、为授权的小程序配置域名时支持配置子域名，例如第三方登记的业务域名如为qq.com，则可以直接将qq.com及其子域名（如xxx.qq.com）也配置到授权的小程序中。
   * </pre>
   */
  public final static String API_SET_WEBVIEW_DOMAIN = "https://api.weixin.qq.com/wxa/setwebviewdomain";

  /**
   * 获取帐号基本信息
   * <pre>
   * GET请求
   * 注意：需要使用1.3环节获取到的新创建小程序appid及authorization_code换取authorizer_refresh_token进而得到authorizer_access_token。
   * </pre>
   */
  public final static String API_GET_ACCOUNT_BASICINFO = "https://api.weixin.qq.com/cgi-bin/account/getaccountbasicinfo";

  /**
   * 绑定微信用户为小程序体验者
   */
  public final static String API_BIND_TESTER = "https://api.weixin.qq.com/wxa/bind_tester";


  /**
   * 解除绑定微信用户为小程序体验者
   */
  public final static String API_UNBIND_TESTER = "https://api.weixin.qq.com/wxa/unbind_tester";


  /**
   * 获取体验者列表
   */
  public final static String API_GET_TESTERLIST = "https://api.weixin.qq.com/wxa/memberauth";

  /**
   * 以下接口为三方平台代小程序实现的代码管理功能
   * <p>
   *     https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1489140610_Uavc4&token=fe774228c66725425675810097f9e48d0737a4bf&lang=zh_CN
   * </p>
   */

  /**
   * 1. 为授权的小程序帐号上传小程序代码
   */
  public final static String API_CODE_COMMIT = "https://api.weixin.qq.com/wxa/commit";

  /**
   * 2. 获取体验小程序的体验二维码
   */
  public final static String API_TEST_QRCODE = "https://api.weixin.qq.com/wxa/get_qrcode";

  /**
   * 3. 获取授权小程序帐号的可选类目
   */
  public final static String API_GET_CATEGORY = "https://api.weixin.qq.com/wxa/get_category";

  /**
   * 4. 获取小程序的第三方提交代码的页面配置（仅供第三方开发者代小程序调用）
   */
  public final static String API_GET_PAGE = "https://api.weixin.qq.com/wxa/get_page";

  /**
   * 5. 将第三方提交的代码包提交审核（仅供第三方开发者代小程序调用）
   */
  public final static String API_SUBMIT_AUDIT = "https://api.weixin.qq.com/wxa/submit_audit";

  /**
   * 7. 查询某个指定版本的审核状态（仅供第三方代小程序调用）
   */
  public final static String API_GET_AUDIT_STATUS = "https://api.weixin.qq.com/wxa/get_auditstatus";

  /**
   * 8. 查询最新一次提交的审核状态（仅供第三方代小程序调用）
   */
  public final static String API_GET_LATEST_AUDIT_STATUS = "https://api.weixin.qq.com/wxa/get_latest_auditstatus";

  /**
   * 9. 发布已通过审核的小程序（仅供第三方代小程序调用）
   */
  public final static String API_RELEASE = "https://api.weixin.qq.com/wxa/release";

  /**
   * 10. 修改小程序线上代码的可见状态（仅供第三方代小程序调用)
   */
  public final static String API_CHANGE_VISITSTATUS = "https://api.weixin.qq.com/wxa/change_visitstatus";

  /**
   * 11.小程序版本回退（仅供第三方代小程序调用）
   */
  public final static String API_REVERT_CODE_RELEASE = "https://api.weixin.qq.com/wxa/revertcoderelease";

  /**
   * 12.查询当前设置的最低基础库版本及各版本用户占比 （仅供第三方代小程序调用）
   */
  public final static String API_GET_WEAPP_SUPPORT_VERSION = "https://api.weixin.qq.com/cgi-bin/wxopen/getweappsupportversion";

  /**
   * 13.设置最低基础库版本（仅供第三方代小程序调用）
   */
  public final static String API_SET_WEAPP_SUPPORT_VERSION = "https://api.weixin.qq.com/cgi-bin/wxopen/setweappsupportversion";

  /**
   * 15.小程序审核撤回
   * <p>
   * 单个帐号每天审核撤回次数最多不超过1次，一个月不超过10次。
   * </p>
   */
  public final static String API_UNDO_CODE_AUDIT = "https://api.weixin.qq.com/wxa/undocodeaudit";

  /**
   * 16.1 小程序分阶段发布-分阶段发布接口
   */
  public final static String API_GRAY_RELEASE = "https://api.weixin.qq.com/wxa/grayrelease";

  /**
   * 16.2 小程序分阶段发布-取消分阶段发布
   */
  public final static String API_REVERT_GRAY_RELEASE = "https://api.weixin.qq.com/wxa/revertgrayrelease";

  /**
   * 16.3 小程序分阶段发布-查询当前分阶段发布详情
   */
  public final static String API_GET_GRAY_RELEASE_PLAN = "https://api.weixin.qq.com/wxa/getgrayreleaseplan";

}
