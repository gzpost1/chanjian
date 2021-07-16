package com.yjtech.wisdom.tourism.wechat.wechat.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wuyongchong on 2019/11/11.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class WechatAppSelectItem implements Serializable {

  /**
   * 小程序ID
   */
  private String appId;
  /**
   * 小程序名称
   */
  private String appName;

}
