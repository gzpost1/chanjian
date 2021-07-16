package com.yjtech.wisdom.tourism.wechat.wxopen.bean.message;

import com.yjtech.wisdom.tourism.wechat.wxcommon.util.xml.XStreamCDataConverter;
import com.yjtech.wisdom.tourism.wechat.wxopen.config.WxOpenProperties;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.crypto.WxOpenCryptUtil;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.xml.XStreamTransformer;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

/**
 * Created by wuyongchong on 2019/9/5.
 */
@Data
@Slf4j
@XStreamAlias("xml")
public class WxOpenXmlMessage implements Serializable {

  @XStreamAlias("AppId")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String appId;

  @XStreamAlias("CreateTime")
  private Long createTime;

  @XStreamAlias("InfoType")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String infoType;

  @XStreamAlias("ComponentVerifyTicket")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String componentVerifyTicket;

  @XStreamAlias("AuthorizerAppid")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String authorizerAppid;

  @XStreamAlias("AuthorizationCode")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String authorizationCode;

  @XStreamAlias("AuthorizationCodeExpiredTime")
  private Long authorizationCodeExpiredTime;

  @XStreamAlias("PreAuthCode")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String preAuthCode;

  // 以下为快速创建小程序接口推送的的信息

  @XStreamAlias("appid")
  private String registAppId;

  @XStreamAlias("status")
  private int status;

  @XStreamAlias("auth_code")
  private String authCode;

  @XStreamAlias("msg")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String msg;

  @XStreamAlias("info")
  private Info info = new Info();

  @XStreamAlias("info")
  @Data
  public static class Info implements Serializable {

    private static final long serialVersionUID = 7706235740094081194L;

    @XStreamAlias("name")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String name;

    @XStreamAlias("code")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String code;

    @XStreamAlias("code_type")
    private int codeType;

    @XStreamAlias("legal_persona_wechat")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String legalPersonaWechat;

    @XStreamAlias("legal_persona_name")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String legalPersonaName;

    @XStreamAlias("component_phone")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String componentPhone;
  }

  public static WxOpenXmlMessage fromXml(String xml) {
    //修改微信变态的消息内容格式，方便解析
    xml = xml.replace("</PicList><PicList>", "");
    return XStreamTransformer.fromXml(WxOpenXmlMessage.class, xml);
  }

  public static WxOpenXmlMessage fromXml(InputStream is) {
    return XStreamTransformer.fromXml(WxOpenXmlMessage.class, is);
  }

  /**
   * 从加密字符串转换
   *
   * @param encryptedXml 密文
   * @param wxOpenProperties 配置属性
   * @param timestamp 时间戳
   * @param nonce 随机串
   * @param msgSignature 签名串
   */
  public static WxOpenXmlMessage fromEncryptedXml(String encryptedXml,
      WxOpenProperties wxOpenProperties, String timestamp, String nonce, String msgSignature) {
    WxOpenCryptUtil cryptUtil = new WxOpenCryptUtil(wxOpenProperties);
    String plainText = cryptUtil.decrypt(msgSignature, timestamp, nonce, encryptedXml);
    log.debug("解密后的原始xml消息内容：{}", plainText);
    return fromXml(plainText);
  }

  public static WxOpenXmlMessage fromEncryptedXml(InputStream is, WxOpenProperties wxOpenProperties,
      String timestamp, String nonce, String msgSignature) {
    try {
      return fromEncryptedXml(IOUtils.toString(is, StandardCharsets.UTF_8),
          wxOpenProperties, timestamp, nonce, msgSignature);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static WxMaMessage fromEncryptedMaXml(String encryptedXml,
      WxOpenProperties wxOpenProperties,
      String timestamp, String nonce, String msgSignature) {
    WxOpenCryptUtil cryptUtil = new WxOpenCryptUtil(wxOpenProperties);
    String plainText = cryptUtil.decrypt(msgSignature, timestamp, nonce, encryptedXml);
    return WxMaMessage.fromXml(plainText);
  }

  public static String wxMpOutXmlMessageToEncryptedXml(WxMpXmlOutMessage message,
      WxOpenProperties wxOpenProperties) {
    String plainXml = message.toXml();
    WxOpenCryptUtil pc = new WxOpenCryptUtil(wxOpenProperties);
    return pc.encrypt(plainXml);
  }

}