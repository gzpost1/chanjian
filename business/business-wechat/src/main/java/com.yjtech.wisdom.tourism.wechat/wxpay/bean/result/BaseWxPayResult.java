package com.yjtech.wisdom.tourism.wechat.wxpay.bean.result;

import com.yjtech.wisdom.tourism.wechat.wxcommon.util.json.WxGsonBuilder;
import com.yjtech.wisdom.tourism.wechat.wxcommon.util.xml.XStreamInitializer;
import com.yjtech.wisdom.tourism.wechat.wxpay.WxPayConstants;
import com.yjtech.wisdom.tourism.wechat.wxpay.exception.WxPayException;
import com.yjtech.wisdom.tourism.wechat.wxpay.service.WxPayService;
import com.yjtech.wisdom.tourism.wechat.wxpay.util.SignUtils;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * 微信支付结果共用属性类. Created by wuyongchong on 2019/9/17.
 */
@Data
public abstract class BaseWxPayResult implements Serializable {

  protected Logger getLogger() {
    return LoggerFactory.getLogger(this.getClass());
  }

  /**
   * <pre>
   * 字段名：返回状态码
   * 变量名：return_code
   * 是否必填：是
   * 类型：String(16)
   * 示例值：SUCCESS
   * 描述：SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
   * </pre>
   */
  @XStreamAlias("return_code")
  protected String returnCode;

  /**
   * <pre>
   * 字段名：返回信息
   * 变量名：return_msg
   * 是否必填：否
   * 类型：String(128)
   * 示例值：签名失败
   * 描述：返回信息，如非空，为错误原因 签名失败 参数格式校验错误
   * </pre>
   */
  @XStreamAlias("return_msg")
  protected String returnMsg;

  /*当return_code为SUCCESS的时候，还会包括以下字段*/

  /**
   * <pre>
   * 字段名：业务结果
   * 变量名：result_code
   * 是否必填：是
   * 类型：String(16)
   * 示例值：SUCCESS
   * 描述：SUCCESS/FAIL
   * </pre>
   */
  @XStreamAlias("result_code")
  private String resultCode;

  /**
   * <pre>
   * 字段名：错误代码
   * 变量名：err_code
   * 是否必填：否
   * 类型：String(32)
   * 描述：详细参见第6节错误列表
   * </pre>
   */
  @XStreamAlias("err_code")
  private String errCode;

  /**
   * <pre>
   * 字段名：错误代码描述
   * 变量名：err_code_des
   * 是否必填：否
   * 类型：String(128)
   * 示例值：系统错误
   * 描述：错误返回的信息描述
   * </pre>
   */
  @XStreamAlias("err_code_des")
  private String errCodeDes;

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
  private String appid;

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
  private String mchId;

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
  private String subAppId;

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
  private String subMchId;

  /**
   * <pre>
   * 字段名：随机字符串.
   * 变量名：nonce_str
   * 是否必填：是
   * 类型：String(32)
   * 示例值：5K8264ILTKCH16CQ2502SI8ZNMTM67VS
   * 描述：微信返回的随机字符串
   * </pre>
   */
  @XStreamAlias("nonce_str")
  private String nonceStr;

  /**
   * <pre>
   * 字段名：签名.
   * 变量名：sign
   * 是否必填：是
   * 类型：String(32)
   * 示例值：C380BEC2BFD727A4B6845133519F3AD6
   * 描述：微信返回的签名
   * </pre>
   */
  @XStreamAlias("sign")
  private String sign;


  /**
   * 微信支付返回的结果xml字符串.
   */
  private String xmlString;

  /**
   * xml的Document对象，用于解析xml文本.
   */
  private Document xmlDoc;

  public static <T extends BaseWxPayResult> T fromXML(String xmlString, Class<T> clz) {
    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(clz);
    T result = (T) xstream.fromXML(xmlString);
    result.setXmlString(xmlString);
    return result;
  }

  public void checkResult(WxPayService wxPayService, String signType, boolean checkSuccess)
      throws WxPayException {
    //校验返回结果签名
    Map<String, String> map = toMap();
    if (getSign() != null && !SignUtils
        .checkSign(map, signType, wxPayService.getConfig().getMchKey())) {
      this.getLogger().debug("校验结果签名失败，参数：{}", toString());
      throw new WxPayException("参数格式校验错误！");
    }
    //校验结果是否成功
    if (checkSuccess) {
      List<String> successStrings = Lists.newArrayList(WxPayConstants.ResultCode.SUCCESS, "");
      if (!successStrings.contains(StringUtils.trimToEmpty(getReturnCode()).toUpperCase())
          || !successStrings.contains(StringUtils.trimToEmpty(getResultCode()).toUpperCase())) {
        StringBuilder errorMsg = new StringBuilder();
        if (getReturnCode() != null) {
          errorMsg.append("返回代码：").append(getReturnCode());
        }
        if (getReturnMsg() != null) {
          errorMsg.append("，返回信息：").append(getReturnMsg());
        }
        if (getResultCode() != null) {
          errorMsg.append("，结果代码：").append(getResultCode());
        }
        if (getErrCode() != null) {
          errorMsg.append("，错误代码：").append(getErrCode());
        }
        if (getErrCodeDes() != null) {
          errorMsg.append("，错误详情：").append(getErrCodeDes());
        }
        this.getLogger().error("\n结果业务代码异常，返回结果：{},\n{}", toString(), errorMsg.toString());
        throw WxPayException.from(this);
      }
    }
  }

  /**
   * 将bean通过保存的xml字符串转换成map.
   *
   * @return the map
   */
  public Map<String, String> toMap() {
    if (StringUtils.isBlank(this.xmlString)) {
      throw new RuntimeException("xml数据有问题，请核实！");
    }

    Map<String, String> result = Maps.newHashMap();
    Document doc = this.getXmlDoc();

    try {
      NodeList list = (NodeList) XPathFactory.newInstance().newXPath()
          .compile("/xml/*")
          .evaluate(doc, XPathConstants.NODESET);
      int len = list.getLength();
      for (int i = 0; i < len; i++) {
        result.put(list.item(i).getNodeName(), list.item(i).getTextContent());
      }
    } catch (XPathExpressionException e) {
      throw new RuntimeException("非法的xml文本内容：" + xmlString);
    }

    return result;
  }

  /**
   * 将xml字符串转换成Document对象，以便读取其元素值.
   */
  private Document getXmlDoc() {
    if (this.xmlDoc != null) {
      return this.xmlDoc;
    }
    try {
      final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setExpandEntityReferences(false);
      factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      this.xmlDoc = factory.newDocumentBuilder()
          .parse(new ByteArrayInputStream(this.xmlString.getBytes(StandardCharsets.UTF_8)));
      return xmlDoc;
    } catch (Exception e) {
      throw new RuntimeException("非法的xml文本内容：\n" + this.xmlString, e);
    }
  }

  /**
   * 获取xml中元素的值.
   *
   * @param path the path
   * @return the xml value
   */
  String getXmlValue(String... path) {
    Document doc = this.getXmlDoc();
    String expression = String.format("/%s//text()", Joiner.on("/").join(path));
    try {
      return (String) XPathFactory
          .newInstance()
          .newXPath()
          .compile(expression)
          .evaluate(doc, XPathConstants.STRING);
    } catch (XPathExpressionException e) {
      throw new RuntimeException("未找到相应路径的文本：" + expression);
    }
  }

  /**
   * 获取xml中元素的值，作为int值返回.
   *
   * @param path the path
   * @return the xml value as int
   */
  Integer getXmlValueAsInt(String... path) {
    String result = this.getXmlValue(path);
    if (StringUtils.isBlank(result)) {
      return null;
    }

    return Integer.valueOf(result);
  }

  @Override
  public String toString() {
    return WxGsonBuilder.create().toJson(this);
  }
}
