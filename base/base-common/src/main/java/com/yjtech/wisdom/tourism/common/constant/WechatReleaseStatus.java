package com.yjtech.wisdom.tourism.common.constant;

/**
 * 发布状态(0：待发布 1:已发布 2:发布失败) Created by wuyongchong on 2019/11/4.
 */
public class WechatReleaseStatus {

  //待发布
  public static final Byte WAIT_RELEASE = Byte.parseByte("0");

  //已发布
  public static final Byte RELEASEED = Byte.parseByte("1");

  //发布失败
  public static final Byte RELEASE_FAIL = Byte.parseByte("2");

}
