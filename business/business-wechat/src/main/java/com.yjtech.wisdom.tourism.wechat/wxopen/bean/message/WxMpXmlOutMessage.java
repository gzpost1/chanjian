package com.yjtech.wisdom.tourism.wechat.wxopen.bean.message;

import com.yjtech.wisdom.tourism.wechat.wxcommon.util.xml.XStreamCDataConverter;
import com.yjtech.wisdom.tourism.wechat.wxopen.builder.outxml.TextBuilder;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.xml.XStreamTransformer;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import java.io.Serializable;
import lombok.Data;

@XStreamAlias("xml")
@Data
public abstract class WxMpXmlOutMessage implements Serializable {

  private static final long serialVersionUID = -381382011286216263L;

  @XStreamAlias("ToUserName")
  @XStreamConverter(value = XStreamCDataConverter.class)
  protected String toUserName;

  @XStreamAlias("FromUserName")
  @XStreamConverter(value = XStreamCDataConverter.class)
  protected String fromUserName;

  @XStreamAlias("CreateTime")
  protected Long createTime;

  @XStreamAlias("MsgType")
  @XStreamConverter(value = XStreamCDataConverter.class)
  protected String msgType;

  /**
   * 获得文本消息builder
   */
  public static TextBuilder TEXT() {
    return new TextBuilder();
  }

  @SuppressWarnings("unchecked")
  public String toXml() {
    return XStreamTransformer.toXml((Class<WxMpXmlOutMessage>) this.getClass(), this);
  }

}
