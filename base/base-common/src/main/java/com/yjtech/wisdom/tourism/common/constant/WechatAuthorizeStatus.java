package com.yjtech.wisdom.tourism.common.constant;

/**
 * 小程序授权状态(0：待授权 1：已授权 2:取消授权) Created by wuyongchong on 2019/11/4.
 */
public class WechatAuthorizeStatus {

  //待授权
  public static final Byte WAIT_AUTHORIZE = Byte.parseByte("0");
  //已授权
  public static final Byte AUTHORIZED = Byte.parseByte("1");
  //取消授权
  public static final Byte CANCEL_AUTHORIZE = Byte.parseByte("2");
}
