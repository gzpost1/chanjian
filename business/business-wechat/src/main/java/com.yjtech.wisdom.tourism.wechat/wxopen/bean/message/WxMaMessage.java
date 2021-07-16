package com.yjtech.wisdom.tourism.wechat.wxopen.bean.message;

import com.yjtech.wisdom.tourism.wechat.wxcommon.util.xml.XStreamCDataConverter;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.json.WxMaGsonBuilder;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.xml.XStreamTransformer;
import com.google.gson.annotations.SerializedName;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import java.io.InputStream;
import java.io.Serializable;
import lombok.Data;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@XStreamAlias("xml")
@Data
public class WxMaMessage implements Serializable {

  private static final long serialVersionUID = -3586245291677274914L;

  @SerializedName("Encrypt")
  @XStreamAlias("Encrypt")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String encrypt;

  @SerializedName("ToUserName")
  @XStreamAlias("ToUserName")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String toUser;

  @SerializedName("FromUserName")
  @XStreamAlias("FromUserName")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String fromUser;

  @SerializedName("CreateTime")
  @XStreamAlias("CreateTime")
  private Integer createTime;

  @SerializedName("MsgType")
  @XStreamAlias("MsgType")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String msgType;

  @SerializedName("MsgDataFormat")
  @XStreamAlias("MsgDataFormat")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String msgDataFormat;

  @SerializedName("Content")
  @XStreamAlias("Content")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String content;

  @SerializedName("MsgId")
  @XStreamAlias("MsgId")
  private Long msgId;

  @SerializedName("PicUrl")
  @XStreamAlias("PicUrl")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String picUrl;

  @SerializedName("MediaId")
  @XStreamAlias("MediaId")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String mediaId;

  @SerializedName("Event")
  @XStreamAlias("Event")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String event;

  @SerializedName("Title")
  @XStreamAlias("Title")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String title;

  @SerializedName("AppId")
  @XStreamAlias("AppId")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String appId;

  @SerializedName("PagePath")
  @XStreamAlias("PagePath")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String pagePath;

  @SerializedName("ThumbUrl")
  @XStreamAlias("ThumbUrl")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String thumbUrl;

  @SerializedName("ThumbMediaId")
  @XStreamAlias("ThumbMediaId")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String thumbMediaId;

  @SerializedName("SessionFrom")
  @XStreamAlias("SessionFrom")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String sessionFrom;

  /**
   * 小程序审核失败的原因
   */
  @SerializedName("Reason")
  @XStreamAlias("Reason")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String reason;

  public static WxMaMessage fromXml(String xml) {
    return XStreamTransformer.fromXml(WxMaMessage.class, xml);
  }

  public static WxMaMessage fromXml(InputStream is) {
    return XStreamTransformer.fromXml(WxMaMessage.class, is);
  }

  public static WxMaMessage fromJson(String json) {
    return WxMaGsonBuilder.create().fromJson(json, WxMaMessage.class);
  }

  @Override
  public String toString() {
    return this.toJson();
  }

  public String toJson() {
    return WxMaGsonBuilder.create().toJson(this);
  }

}
