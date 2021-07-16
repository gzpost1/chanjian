package com.yjtech.wisdom.tourism.wechat.wxpay.bean.result;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by wuyongchong on 2019/9/16.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxPayUnifiedOrderResult extends BaseWxPayResult {

  /**
   * <pre>
   * 字段名：设备号.
   * 变量名：device_info
   * 是否必填：否
   * 类型：String(32)
   * 示例值：013467007045764
   * 描述：调用接口提交的终端设备号
   * </pre>
   */
  @XStreamAlias("device_info")
  private String deviceInfo;

  /**
   * <pre>
   * 字段名：交易类型
   * 变量名：trade_type
   * 是否必填：是
   * 类型：String(16)
   * 示例值：JSAPI
   * 描述：调用接口提交的交易类型 JSAPI--JSAPI支付（或小程序支付）、NATIVE--Native支付、APP--app支付，MWEB--H5支付
   * </pre>
   */
  @XStreamAlias("trade_type")
  private String tradeType;

  /**
   * <pre>
   * 字段名：预支付交易会话标识
   * 变量名：prepay_id
   * 是否必填：是
   * 类型：String(64)
   * 示例值：wx201410272009395522657a690389285100
   * 描述：微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
   * </pre>
   */
  @XStreamAlias("prepay_id")
  private String prepayId;

  /**
   * <pre>
   * 字段名：二维码链接
   * 变量名：code_url
   * 类型：String(64)
   * 示例值：weixin://wxpay/bizpayurl/up?pr=NwY5Mz9&groupid=00
   * 描述：trade_type=NATIVE时有返回，此url用于生成支付二维码，然后提供给用户进行扫码支付。
   * 注意：code_url的值并非固定，使用时按照URL格式转成二维码即可
   * </pre>
   */
  @XStreamAlias("code_url")
  private String codeURL;

  /**
   * <pre>
   * 字段名：支付跳转链接
   * 变量名：mweb_url
   * 类型：String(64)
   * 示例值：https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx2016121516420242444321ca0631331346&package=1405458241
   * 描述：mweb_url为拉起微信支付收银台的中间页面，可通过访问该url来拉起微信客户端，完成支付,mweb_url的有效期为5分钟
   * </pre>
   */
  @XStreamAlias("mweb_url")
  private String mwebUrl;

}
