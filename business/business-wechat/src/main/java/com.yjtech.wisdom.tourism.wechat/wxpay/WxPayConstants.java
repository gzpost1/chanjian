package com.yjtech.wisdom.tourism.wechat.wxpay;

import com.google.common.collect.Lists;
import java.util.List;

/**
 * 微信支付常量类 Created by wuyongchong on 2019/9/16.
 */
public class WxPayConstants {

  /**
   * 交易类型.
   */
  public static class TradeType {

    /**
     * 原生扫码支付.
     */
    public static final String NATIVE = "NATIVE";

    /**
     * App支付.
     */
    public static final String APP = "APP";

    /**
     * 公众号支付/小程序支付.
     */
    public static final String JSAPI = "JSAPI";

    /**
     * H5支付.
     */
    public static final String MWEB = "MWEB";

    /**
     * 刷卡支付. 刷卡支付有单独的支付接口，不调用统一下单接口
     */
    public static final String MICROPAY = "MICROPAY";
  }

  /**
   * 签名类型.
   */
  public static class SignType {

    /**
     * The constant HMAC_SHA256.
     */
    public static final String HMAC_SHA256 = "HMAC-SHA256";
    /**
     * The constant MD5.
     */
    public static final String MD5 = "MD5";
    /**
     * The constant ALL_SIGN_TYPES.
     */
    public static final List<String> ALL_SIGN_TYPES = Lists.newArrayList(HMAC_SHA256, MD5);
  }

  /**
   * 限定支付方式.
   */
  public static class LimitPay {

    /**
     * no_credit--指定不能使用信用卡支付.
     */
    public static final String NO_CREDIT = "no_credit";
  }

  /**
   * 业务结果代码.
   */
  public static class ResultCode {

    /**
     * 成功.
     */
    public static final String SUCCESS = "SUCCESS";

    /**
     * 失败.
     */
    public static final String FAIL = "FAIL";
  }

  /**
   * 退款资金来源.
   */
  public static class RefundAccountSource {

    /**
     * 可用余额退款/基本账户.
     */
    public static final String RECHARGE_FUNDS = "REFUND_SOURCE_RECHARGE_FUNDS";

    /**
     * 未结算资金退款.
     */
    public static final String UNSETTLED_FUNDS = "REFUND_SOURCE_UNSETTLED_FUNDS";

  }

  /**
   * 退款渠道.
   */
  public static class RefundChannel {

    /**
     * 原路退款.
     */
    public static final String ORIGINAL = "ORIGINAL";

    /**
     * 退回到余额.
     */
    public static final String BALANCE = "BALANCE";

    /**
     * 原账户异常退到其他余额账户.
     */
    public static final String OTHER_BALANCE = "OTHER_BALANCE";

    /**
     * 原银行卡异常退到其他银行卡.
     */
    public static final String OTHER_BANKCARD = "OTHER_BANKCARD";
  }

  /**
   * 交易状态.
   */
  public static class WxpayTradeStatus {

    /**
     * 支付成功.
     */
    public static final String SUCCESS = "SUCCESS";

    /**
     * 支付失败(其他原因，如银行返回失败).
     */
    public static final String PAY_ERROR = "PAYERROR";

    /**
     * 用户支付中.
     */
    public static final String USER_PAYING = "USERPAYING";

    /**
     * 已关闭.
     */
    public static final String CLOSED = "CLOSED";

    /**
     * 未支付.
     */
    public static final String NOTPAY = "NOTPAY";

    /**
     * 转入退款.
     */
    public static final String REFUND = "REFUND";

    /**
     * 已撤销（刷卡支付）.
     */
    public static final String REVOKED = "REVOKED";
  }

  /**
   * 退款状态.
   */
  public static class RefundStatus {

    /**
     * 退款成功.
     */
    public static final String SUCCESS = "SUCCESS";

    /**
     * 退款关闭.
     */
    public static final String REFUND_CLOSE = "REFUNDCLOSE";

    /**
     * 退款处理中.
     */
    public static final String PROCESSING = "PROCESSING";

    /**
     * 退款异常. 退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，可前往商户平台（pay.weixin.qq.com）-交易中心，手动处理此笔退款。
     */
    public static final String CHANGE = "CHANGE";
  }
}
