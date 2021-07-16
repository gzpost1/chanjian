package com.yjtech.wisdom.tourism.wechat.wxcommon.util.json;

import com.yjtech.wisdom.tourism.wechat.wxcommon.error.WxError;
import com.yjtech.wisdom.tourism.wechat.wxcommon.util.json.adapter.WxErrorAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by wuyongchong on 2019/9/6.
 */
public class WxGsonBuilder {

  private static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(WxError.class, new WxErrorAdapter());
  }

  public static Gson create() {
    return INSTANCE.create();
  }
}
