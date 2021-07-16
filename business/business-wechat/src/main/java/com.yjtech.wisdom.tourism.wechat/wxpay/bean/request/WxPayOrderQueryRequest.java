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
public class WxPayOrderQueryRequest extends BaseWxPayRequest {

  /**
   * <pre>
   * 微信订单号
   * transaction_id
   * 二选一
   * String(32)
   * 1009660380201506130728806387
   * 微信的订单号，优先使用
   * </pre>
   */
  @XStreamAlias("transaction_id")
  private String transactionId;

  /**
   * <pre>
   * 商户订单号
   * out_trade_no
   * 二选一
   * String(32)
   * 20150806125346
   * 商户系统内部的订单号，当没提供transaction_id时需要传这个。
   * </pre>
   */
  @XStreamAlias("out_trade_no")
  private String outTradeNo;

  @Override
  protected void checkConstraints() throws WxPayException {
    if ((StringUtils.isBlank(transactionId) && StringUtils.isBlank(outTradeNo)) ||
        (StringUtils.isNotBlank(transactionId) && StringUtils.isNotBlank(outTradeNo))) {
      throw new WxPayException("transaction_id 和 out_trade_no 不能同时存在或同时为空，必须二选一");
    }
  }
}
