package com.yjtech.wisdom.tourism.wechat.wxpay.bean.request;

import com.yjtech.wisdom.tourism.wechat.wxpay.exception.WxPayException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by wuyongchong on 2019/9/17.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class WxPayRefundQueryRequest extends BaseWxPayRequest {
  //************以下四选一************
  /**
   * <pre>
   * 微信订单号
   * transaction_id
   * String(32)
   * 1217752501201407033233368018
   * 微信订单号
   * </pre>
   */
  @XStreamAlias("transaction_id")
  private String transactionId;

  /**
   * <pre>
   * 商户订单号
   * out_trade_no
   * String(32)
   * 1217752501201407033233368018
   * 商户系统内部的订单号
   * </pre>
   */
  @XStreamAlias("out_trade_no")
  private String outTradeNo;

  /**
   * <pre>
   * 商户退款单号
   * out_refund_no
   * String(32)
   * 1217752501201407033233368018
   * 商户侧传给微信的退款单号
   * </pre>
   */
  @XStreamAlias("out_refund_no")
  private String outRefundNo;

  /**
   * <pre>
   * 微信退款单号
   * refund_id
   * String(28)
   * 1217752501201407033233368018
   * 微信生成的退款单号，在申请退款接口有返回
   * </pre>
   */
  @XStreamAlias("refund_id")
  private String refundId;

  @Override
  protected void checkConstraints() throws WxPayException {
    if ((StringUtils.isBlank(transactionId) && StringUtils.isBlank(outTradeNo)
        && StringUtils.isBlank(outRefundNo) && StringUtils.isBlank(refundId)) ||
        (StringUtils.isNotBlank(transactionId) && StringUtils.isNotBlank(outTradeNo)
            && StringUtils.isNotBlank(outRefundNo) && StringUtils.isNotBlank(refundId))) {
      throw new WxPayException("transaction_id，out_trade_no，out_refund_no，refund_id 必须四选一");
    }

  }
}
