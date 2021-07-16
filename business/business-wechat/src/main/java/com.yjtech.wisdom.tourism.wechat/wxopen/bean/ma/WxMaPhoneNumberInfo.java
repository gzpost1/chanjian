package com.yjtech.wisdom.tourism.wechat.wxopen.bean.ma;

import com.yjtech.wisdom.tourism.wechat.wxopen.util.json.WxMaGsonBuilder;
import java.io.Serializable;
import lombok.Data;

/**
 * 微信用户绑定的手机号相关信息
 */
@Data
public class WxMaPhoneNumberInfo implements Serializable {

  private static final long serialVersionUID = 6719822331555402137L;

  private String phoneNumber;
  private String purePhoneNumber;
  private String countryCode;
  private Watermark watermark;

  public static WxMaPhoneNumberInfo fromJson(String json) {
    return WxMaGsonBuilder.create().fromJson(json, WxMaPhoneNumberInfo.class);
  }

  @Data
  public static class Watermark {

    private String timestamp;
    private String appid;
  }
}
