package com.yjtech.wisdom.tourism.wechat.wxpay.bean.request;

import com.yjtech.wisdom.tourism.wechat.wxcommon.error.WxErrorException;
import com.yjtech.wisdom.tourism.wechat.wxcommon.util.RequiredUtils;
import com.yjtech.wisdom.tourism.wechat.wxcommon.util.json.WxGsonBuilder;
import com.yjtech.wisdom.tourism.wechat.wxcommon.util.xml.XStreamInitializer;
import com.yjtech.wisdom.tourism.wechat.wxpay.WxPayConstants.SignType;
import com.yjtech.wisdom.tourism.wechat.wxpay.config.WxPayConfig;
import com.yjtech.wisdom.tourism.wechat.wxpay.exception.WxPayException;
import com.yjtech.wisdom.tourism.wechat.wxpay.util.SignUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.Serializable;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 微信支付请求对象共用的参数存放类 Created by wuyongchong on 2019/9/17.
 */
@Data
public abstract class BaseWxPayRequest implements Serializable {

  /**
   * <pre>
   * 字段名：公众账号ID或者服务商的APPID
   * 变量名：appid
   * 是否必填：是
   * 类型：String(32)
   * 示例值：wxd678efh567hg6787
   * 描述：微信分配的公众账号ID（服务商商户的APPID）
   * </pre>
   */
  @XStreamAlias("appid")
  protected String appid;

  /**
   * <pre>
   * 字段名：商户号.
   * 变量名：mch_id
   * 是否必填：是
   * 类型：String(32)
   * 示例值：1230000109
   * 描述：微信支付分配的商户号
   * </pre>
   */
  @XStreamAlias("mch_id")
  protected String mchId;

  /**
   * <pre>
   * 字段名：服务商模式下的子商户公众账号ID或者小程序的APPID
   * 变量名：sub_appid
   * 是否必填：否
   * 类型：String(32)
   * 示例值：wxd678efh567hg6787
   * 描述：微信分配的子商户公众账号ID（当前调起支付的小程序APPID）
   * </pre>
   */
  @XStreamAlias("sub_appid")
  protected String subAppId;

  /**
   * <pre>
   * 字段名：服务商模式下的子商户号
   * 变量名：sub_mch_id
   * 是否必填：是
   * 类型：String(32)
   * 示例值：1230000109
   * 描述：微信支付分配的子商户号，开发者模式下必填
   * </pre>
   */
  @XStreamAlias("sub_mch_id")
  protected String subMchId;

  /**
   * <pre>
   * 字段名：随机字符串.
   * 变量名：nonce_str
   * 是否必填：是
   * 类型：String(32)
   * 示例值：5K8264ILTKCH16CQ2502SI8ZNMTM67VS
   * 描述：随机字符串，不长于32位。推荐随机数生成算法
   * </pre>
   */
  @XStreamAlias("nonce_str")
  protected String nonceStr;

  /**
   * <pre>
   * 字段名：签名.
   * 变量名：sign
   * 是否必填：是
   * 类型：String(32)
   * 示例值：C380BEC2BFD727A4B6845133519F3AD6
   * 描述：签名，详见签名生成算法
   * </pre>
   */
  @XStreamAlias("sign")
  protected String sign;

  /**
   * <pre>
   * 签名类型.
   * sign_type
   * 否
   * String(32)
   * HMAC-SHA256
   * 签名类型，目前支持HMAC-SHA256和MD5
   * </pre>
   */
  @XStreamAlias("sign_type")
  private String signType;

  /**
   * 检查约束情况.
   *
   * @throws WxPayException the wx pay exception
   */
  protected abstract void checkConstraints() throws WxPayException;

  /**
   * 签名时，是否忽略appid.
   *
   * @return the boolean
   */
  protected boolean ignoreAppid() {
    return false;
  }

  /**
   * 签名时，忽略的参数.
   *
   * @return the string [ ]
   */
  protected String[] getIgnoredParamsForSign() {
    return new String[0];
  }

  public void checkAndSign(WxPayConfig config) throws WxPayException {
    //检查@Required必填字段
    try {
      RequiredUtils.checkRequiredFields(this);
    } catch (WxErrorException e) {
      throw new WxPayException(e.getError().getErrorMsg(), e);
    }
    //检查自定义约束情况.
    this.checkConstraints();

    if (!ignoreAppid()) {
      if (StringUtils.isBlank(getAppid())) {
        this.setAppid(config.getAppId());
      }
    }

    if (StringUtils.isBlank(getMchId())) {
      this.setMchId(config.getMchId());
    }

    if (StringUtils.isBlank(getSubAppId())) {
      this.setSubAppId(config.getSubAppId());
    }

    if (StringUtils.isBlank(getSubMchId())) {
      this.setSubMchId(config.getSubMchId());
    }

    if (StringUtils.isBlank(getSignType())) {
      if (config.getSignType() != null && !SignType.ALL_SIGN_TYPES.contains(config.getSignType())) {
        throw new WxPayException("非法的signType配置：" + config.getSignType() + "，请检查配置！");
      }
      this.setSignType(StringUtils.trimToNull(config.getSignType()));
    } else {
      if (!SignType.ALL_SIGN_TYPES.contains(this.getSignType())) {
        throw new WxPayException("非法的sign_type参数：" + this.getSignType());
      }
    }

    if (StringUtils.isBlank(getNonceStr())) {
      this.setNonceStr(String.valueOf(System.currentTimeMillis()));
    }

    //设置签名字段的值
    this.setSign(SignUtils
        .createSign(this, this.getSignType(), config.getMchKey(), this.getIgnoredParamsForSign()));
  }

  public String toXML() {
    XStream xstream = XStreamInitializer.getInstance();
    //涉及到服务商模式的两个参数，在为空值时置为null，以免在请求时将空值传给微信服务器
    this.setSubAppId(StringUtils.trimToNull(this.getSubAppId()));
    this.setSubMchId(StringUtils.trimToNull(this.getSubMchId()));
    xstream.processAnnotations(this.getClass());
    return xstream.toXML(this);
  }

  @Override
  public String toString() {
    return WxGsonBuilder.create().toJson(this);
  }
}
