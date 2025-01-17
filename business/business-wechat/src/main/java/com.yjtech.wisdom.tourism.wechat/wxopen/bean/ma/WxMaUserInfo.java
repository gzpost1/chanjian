package com.yjtech.wisdom.tourism.wechat.wxopen.bean.ma;

import com.yjtech.wisdom.tourism.wechat.wxopen.util.json.WxMaGsonBuilder;
import java.io.Serializable;
import lombok.Data;

/**
 * 微信用户信息
 */
@Data
public class WxMaUserInfo implements Serializable {

  private static final long serialVersionUID = 6719822331555402137L;

  private String openId;
  private String nickName;
  private String gender;
  private String language;
  private String city;
  private String province;
  private String country;
  private String avatarUrl;
  private String unionId;
  private Watermark watermark;

  public static WxMaUserInfo fromJson(String json) {
    return WxMaGsonBuilder.create().fromJson(json, WxMaUserInfo.class);
  }

  @Data
  public static class Watermark {

    private String timestamp;
    private String appid;
  }
}
