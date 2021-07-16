package com.yjtech.wisdom.tourism.wechat.wxpay.bean.order;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Builder;
import lombok.Data;

/**
 * Created by wuyongchong on 2019/9/16.
 */
@Data
@Builder
public class WxPayOrderResult {

  private String appId;
  private String timeStamp;
  private String nonceStr;
  /**
   * 由于package为java保留关键字，因此改为packageValue. 前端使用时记得要更改为package
   */
  @XStreamAlias("package")
  private String packageValue;
  private String signType;
  private String paySign;
}
