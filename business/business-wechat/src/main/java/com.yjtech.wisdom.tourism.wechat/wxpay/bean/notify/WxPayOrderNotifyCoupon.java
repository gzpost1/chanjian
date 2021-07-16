package com.yjtech.wisdom.tourism.wechat.wxpay.bean.notify;

import com.yjtech.wisdom.tourism.wechat.wxcommon.util.json.WxGsonBuilder;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wuyongchong on 2019/9/17.
 */
@Data
@NoArgsConstructor
public class WxPayOrderNotifyCoupon implements Serializable {
  private String couponId;
  private String couponType;
  private Integer couponFee;

  /**
   * To map map.
   *
   * @param index the index
   * @return the map
   */
  public Map<String, String> toMap(int index) {
    Map<String, String> map = new HashMap<>();
    map.put("coupon_id_" + index, this.getCouponId());
    map.put("coupon_type_" + index, this.getCouponType());
    map.put("coupon_fee_" + index, this.getCouponFee() + "");
    return map;
  }

  @Override
  public String toString() {
    return WxGsonBuilder.create().toJson(this);
  }
}
