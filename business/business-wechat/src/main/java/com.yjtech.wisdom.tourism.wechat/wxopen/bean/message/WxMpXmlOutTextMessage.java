package com.yjtech.wisdom.tourism.wechat.wxopen.bean.message;

import com.yjtech.wisdom.tourism.wechat.wxcommon.util.xml.XStreamCDataConverter;
import com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenConstants;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@XStreamAlias("xml")
@Data
@EqualsAndHashCode(callSuper = true)
public class WxMpXmlOutTextMessage extends WxMpXmlOutMessage {

  private static final long serialVersionUID = -3972786455288763361L;

  @XStreamAlias("Content")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String content;

  public WxMpXmlOutTextMessage() {
    this.msgType = WxOpenConstants.XmlMsgType.TEXT;
  }

}
