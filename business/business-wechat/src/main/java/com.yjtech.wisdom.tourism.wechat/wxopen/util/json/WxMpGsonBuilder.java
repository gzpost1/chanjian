package com.yjtech.wisdom.tourism.wechat.wxopen.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by wuyongchong on 2019/9/6.
 */
public class WxMpGsonBuilder {
  private static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();

  }
  public static Gson create() {
    return INSTANCE.create();
  }
}
