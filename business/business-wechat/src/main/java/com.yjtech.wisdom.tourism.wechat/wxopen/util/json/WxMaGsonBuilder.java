package com.yjtech.wisdom.tourism.wechat.wxopen.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WxMaGsonBuilder {

  private static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();
  }

  public static Gson create() {
    return INSTANCE.create();
  }

}
