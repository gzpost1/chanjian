package com.yjtech.wisdom.tourism.wechat.wxopen.bean.result;

import com.yjtech.wisdom.tourism.wechat.wxopen.util.json.WxOpenGsonBuilder;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信开放平台小程序第三方提交代码的页面配置列表.
 *
 * @author yqx
 * @date 2018/9/12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxOpenMaPageListResult extends WxOpenResult {
  private static final long serialVersionUID = 6982848180319905444L;

  @SerializedName("page_list")
  List<String> pageList;

  @Override
  public String toString() {
    return WxOpenGsonBuilder.create().toJson(this);
  }

}
