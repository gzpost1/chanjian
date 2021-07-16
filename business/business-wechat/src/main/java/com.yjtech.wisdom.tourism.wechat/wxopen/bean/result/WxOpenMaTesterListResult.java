package com.yjtech.wisdom.tourism.wechat.wxopen.bean.result;

import com.yjtech.wisdom.tourism.wechat.wxopen.bean.ma.WxOpenMaMember;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.json.WxOpenGsonBuilder;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信开放平台小程序体验者列表返回.
 *
 * @author yqx
 * @date 2018/9/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxOpenMaTesterListResult extends WxOpenResult {
  private static final long serialVersionUID = -613936397557067111L;

  @SerializedName("members")
  List<WxOpenMaMember> membersList;

  @Override
  public String toString() {
    return WxOpenGsonBuilder.create().toJson(this);
  }

}
