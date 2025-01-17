package com.yjtech.wisdom.tourism.wechat.wxpay.bean.notify;

import com.yjtech.wisdom.tourism.wechat.wxcommon.util.json.WxGsonBuilder;
import com.yjtech.wisdom.tourism.wechat.wxcommon.util.xml.XStreamInitializer;
import com.yjtech.wisdom.tourism.wechat.wxpay.WxPayConstants;
import com.yjtech.wisdom.tourism.wechat.wxpay.bean.result.BaseWxPayResult;
import com.yjtech.wisdom.tourism.wechat.wxpay.converter.WxPayOrderNotifyResultConverter;
import com.yjtech.wisdom.tourism.wechat.wxpay.exception.WxPayException;
import com.yjtech.wisdom.tourism.wechat.wxpay.service.WxPayService;
import com.yjtech.wisdom.tourism.wechat.wxpay.util.SignUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 支付结果通用通知 Created by wuyongchong on 2019/9/17.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxPayOrderNotifyResult extends BaseWxPayResult {

  /**
   * <pre>
   * 字段名：设备号.
   * 变量名：device_info
   * 是否必填：否
   * 类型：String(32)
   * 示例值：013467007045764
   * 描述：微信支付分配的终端设备号，
   * </pre>
   */
  @XStreamAlias("device_info")
  private String deviceInfo;

  /**
   * <pre>
   * 字段名：用户标识.
   * 变量名：openid
   * 是否必填：是
   * 类型：String(128)
   * 示例值：wxd930ea5d5a258f4f
   * 描述：用户在商户appid下的唯一标识
   * </pre>
   */
  @XStreamAlias("openid")
  private String openid;

  /**
   * <pre>
   * 字段名：是否关注公众账号.
   * 变量名：is_subscribe
   * 是否必填：否
   * 类型：String(1)
   * 示例值：Y
   * 描述：用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
   * </pre>
   */
  @XStreamAlias("is_subscribe")
  private String isSubscribe;

  /**
   * <pre>
   * 字段名：用户子标识.
   * 变量名：sub_openid
   * 是否必填：是
   * 类型：String(128)
   * 示例值：wxd930ea5d5a258f4f
   * 描述：用户在子商户appid下的唯一标识
   * </pre>
   */
  @XStreamAlias("sub_openid")
  private String subOpenid;

  /**
   * <pre>
   * 字段名：是否关注子公众账号.
   * 变量名：sub_is_subscribe
   * 是否必填：否
   * 类型：String(1)
   * 示例值：Y
   * 描述：用户是否关注子公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
   * </pre>
   */
  @XStreamAlias("sub_is_subscribe")
  private String subIsSubscribe;


  /**
   * <pre>
   * 字段名：交易类型.
   * 变量名：trade_type
   * 是否必填：是
   * 类型：String(16)
   * 示例值：JSAPI
   * JSA描述：PI、NATIVE、APP
   * </pre>
   */
  @XStreamAlias("trade_type")
  private String tradeType;

  /**
   * <pre>
   * 字段名：付款银行.
   * 变量名：bank_type
   * 是否必填：是
   * 类型：String(16)
   * 示例值：CMC
   * 描述：银行类型，采用字符串类型的银行标识，银行类型见银行列表
   * </pre>
   */
  @XStreamAlias("bank_type")
  private String bankType;

  /**
   * <pre>
   * 字段名：订单金额.
   * 变量名：total_fee
   * 是否必填：是
   * 类型：Int
   * 示例值：100
   * 描述：订单总金额，单位为分
   * </pre>
   */
  @XStreamAlias("total_fee")
  private Integer totalFee;
  /**
   * <pre>
   * 字段名：应结订单金额.
   * 变量名：settlement_total_fee
   * 是否必填：否
   * 类型：Int
   * 示例值：100
   * 描述：应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
   * </pre>
   */
  @XStreamAlias("settlement_total_fee")
  private Integer settlementTotalFee;
  /**
   * <pre>
   * 字段名：货币种类.
   * 变量名：fee_type
   * 是否必填：否
   * 类型：String(8)
   * 示例值：CNY
   * 描述：货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
   * </pre>
   */
  @XStreamAlias("fee_type")
  private String feeType;
  /**
   * <pre>
   * 字段名：现金支付金额.
   * 变量名：cash_fee
   * 是否必填：是
   * 类型：Int
   * 示例值：100
   * 描述：现金支付金额订单现金支付金额，详见支付金额
   * </pre>
   */
  @XStreamAlias("cash_fee")
  private Integer cashFee;
  /**
   * <pre>
   * 字段名：现金支付货币类型.
   * 变量名：cash_fee_type
   * 是否必填：否
   * 类型：String(16)
   * 示例值：CNY
   * 描述：货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
   * </pre>
   */
  @XStreamAlias("cash_fee_type")
  private String cashFeeType;
  /**
   * <pre>
   * 字段名：总代金券金额.
   * 变量名：coupon_fee
   * 是否必填：否
   * 类型：Int
   * 示例值：10
   * 描述：代金券金额<=订单金额，订单金额-代金券金额=现金支付金额，详见支付金额
   * </pre>
   */
  @XStreamAlias("coupon_fee")
  private Integer couponFee;

  /**
   * <pre>
   * 字段名：代金券使用数量.
   * 变量名：coupon_count
   * 是否必填：否
   * 类型：Int
   * 示例值：1
   * 描述：代金券使用数量
   * </pre>
   */
  @XStreamAlias("coupon_count")
  private Integer couponCount;

  private List<WxPayOrderNotifyCoupon> couponList;

  /**
   * <pre>
   * 字段名：微信支付订单号.
   * 变量名：transaction_id
   * 是否必填：是
   * 类型：String(32)
   * 示例值：1217752501201407033233368018
   * 描述：微信支付订单号
   * </pre>
   */
  @XStreamAlias("transaction_id")
  private String transactionId;

  /**
   * <pre>
   * 字段名：商户订单号.
   * 变量名：out_trade_no
   * 是否必填：是
   * 类型：String(32)
   * 示例值：1212321211201407033568112322
   * 描述：商户系统的订单号，与请求一致。
   * </pre>
   */
  @XStreamAlias("out_trade_no")
  private String outTradeNo;
  /**
   * <pre>
   * 字段名：商家数据包.
   * 变量名：attach
   * 是否必填：否
   * 类型：String(128)
   * 示例值：123456
   * 描述：商家数据包，原样返回
   * </pre>
   */
  @XStreamAlias("attach")
  private String attach;

  /**
   * <pre>
   * 字段名：支付完成时间.
   * 变量名：time_end
   * 是否必填：是
   * 类型：String(14)
   * 示例值：20141030133525
   * 描述：支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
   * </pre>
   */
  @XStreamAlias("time_end")
  private String timeEnd;

  /**
   * <pre>
   * 字段名：接口版本号.
   * 变量名：version
   * 类型：String(32)
   * 示例值：1.0
   * 更多信息，详见文档：https://pay.weixin.qq.com/wiki/doc/api/danpin.php?chapter=9_101&index=1
   * </pre>
   */
  @XStreamAlias("version")
  private String version;

  @Override
  public void checkResult(WxPayService wxPayService, String signType, boolean checkSuccess)
      throws WxPayException {
    //防止伪造成功通知
    if (WxPayConstants.ResultCode.SUCCESS.equals(getReturnCode()) && getSign() == null) {
      throw new WxPayException("伪造的通知！");
    }
    super.checkResult(wxPayService, signType, checkSuccess);
  }

  /**
   * From xml wx pay order notify result.
   *
   * @param xmlString the xml string
   * @return the wx pay order notify result
   */
  public static WxPayOrderNotifyResult fromXML(String xmlString) {
    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxPayOrderNotifyResult.class);
    xstream.registerConverter(
        new WxPayOrderNotifyResultConverter(xstream.getMapper(), xstream.getReflectionProvider()));
    WxPayOrderNotifyResult result = (WxPayOrderNotifyResult) xstream.fromXML(xmlString);
    result.setXmlString(xmlString);
    return result;
  }

  @Override
  public Map<String, String> toMap() {
    Map<String, String> resultMap = SignUtils.xmlBean2Map(this);
    if (this.getCouponCount() != null && this.getCouponCount() > 0) {
      for (int i = 0; i < this.getCouponCount(); i++) {
        WxPayOrderNotifyCoupon coupon = couponList.get(i);
        resultMap.putAll(coupon.toMap(i));
      }
    }
    return resultMap;
  }

  @Override
  public String toString() {
    return WxGsonBuilder.create().toJson(this);
  }
}
