package com.yjtech.wisdom.tourism.wechat.wxopen.builder.kefu;

import com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenConstants.KefuMsgType;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.message.WxMaKefuMessage;

/**
 * 图片消息builder.
 */
public final class KefuImageMessageBuilder extends KefuBaseMessageBuilder<KefuImageMessageBuilder> {

  private String mediaId;

  public KefuImageMessageBuilder() {
    this.msgType = KefuMsgType.IMAGE;
  }

  public KefuImageMessageBuilder mediaId(String mediaId) {
    this.mediaId = mediaId;
    return this;
  }

  @Override
  public WxMaKefuMessage build() {
    WxMaKefuMessage m = super.build();
    m.setImage(new WxMaKefuMessage.KfImage(this.mediaId));
    return m;
  }
}
