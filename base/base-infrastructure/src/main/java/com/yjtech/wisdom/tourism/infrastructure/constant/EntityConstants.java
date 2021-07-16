package com.yjtech.wisdom.tourism.infrastructure.constant;

/** Created by yingcan on 2019/10/22. */
public abstract class EntityConstants {

  public static final Byte NOT_DELETED = Byte.parseByte("0");
  public static final Byte DELETED = Byte.parseByte("1");

  public static final Byte ENABLED = Byte.parseByte("1");
  public static final Byte DISABLED = Byte.parseByte("0");

  public static final String APIID = "c3q4d23761f14cdfa2jai2p9b8n88jgl";
  public static final String APIKEY = "8654b47b44f01518";
  public static final String DEFAULT_PASSWD = "User135792468";
  public static final String ENCRYPT_KEY = "yunjingwenlu_bigdata_666888";

  /** 小程序app_id,app_secret */
  public static final String APP_ID = "wxaf9a9fbf7d46ad91";

  public static final String APP_SECRET = "c2d07354c497daeeebfc47b0539015d9";

  /** 腾讯客情小程序app_id,app_secret */
  public static final String TX_APP_ID = "wx8c157aec6ff2faff";

  public static final String TX_APP_SECRET = "92e77460848d22f51e04623948210c36";

  public static final Byte FRONT_DOMAIN = Byte.parseByte("2");
  public static final Byte BACK_DOMAIN = Byte.parseByte("1");

  /** 审核资源id */
  public static final Long AUDIT_RESOURCE_ID = 1247502779719245825L;

  // 港澳区域系统编码
  public static final String GANG_AO_AREA_CODE = "010000000000";

  // 权限分隔符，如/sys/user/create;/sys/role/listForSelect
  public static final String PERMISSION_PATH_SEPARATOR = ";";

  // 区县管理员角色id
  public static final Long AREA_ROLE_ID = -2L;

  // 超级管理员的userID
  public static final Long ADMIN_USER_ID = -1L;

  // 否
  public static final Byte NO = 0;
  // 是
  public static final Byte YES = 1;

  // 默认用户(或者没有获取到当前用户)
  public static final Long DEFAULT_SYS_USER_ID = Long.parseLong("-1");

  // 平台用户
  public static final Byte USER_BACKEND = Byte.parseByte("1");
  // 区县用户
  public static final Byte USER_BIGSCREEN = Byte.parseByte("2");

  // 后端登录
  public static final Byte LOGIN_BACKEND = Byte.parseByte("1");
  // 大屏登录
  public static final Byte LOGIN_BIGSCREEN = Byte.parseByte("2");

  // 是管理员
  public static final Byte IS_ADMIN = Byte.parseByte("1");
  // 不是管理员
  public static final Byte NOT_ADMIN = Byte.parseByte("0");

  // 待审核
  public static final Byte AUDIT_AWAIT = Byte.parseByte("0");
  // 审核通过
  public static final Byte AUDIT_SUCCESS = Byte.parseByte("1");
  // 审核拒绝
  public static final Byte AUDIT_REFUSE = Byte.parseByte("2");

  public static final Long ROOT_ORGANIZATION_ID = 0L;
}
