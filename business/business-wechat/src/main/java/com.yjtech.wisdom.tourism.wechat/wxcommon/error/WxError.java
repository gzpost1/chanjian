package com.yjtech.wisdom.tourism.wechat.wxcommon.error;

import com.yjtech.wisdom.tourism.wechat.wxcommon.util.json.WxGsonBuilder;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by wuyongchong on 2019/9/6.
 */
@Data
@Builder
public class WxError implements Serializable {

  private static final long serialVersionUID = 7869786563361406291L;

  private int errorCode;

  private String errorMsg;

  private String errorMsgEn;

  private String json;

  public static WxError fromJson(String json) {
    final WxError wxError = WxGsonBuilder.create().fromJson(json, WxError.class);
    if (StringUtils.isNotEmpty(wxError.getErrorMsg())) {
      wxError.setErrorMsgEn(wxError.getErrorMsg());
    }
    return wxError;
  }

  @Override
  public String toString() {
    if (this.json != null) {
      return this.json;
    }
    return "错误: Code=" + this.errorCode + ", Msg=" + this.errorMsg;
  }

}