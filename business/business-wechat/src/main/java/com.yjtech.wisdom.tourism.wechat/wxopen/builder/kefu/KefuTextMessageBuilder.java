package com.yjtech.wisdom.tourism.wechat.wxopen.builder.kefu;

import com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenConstants.KefuMsgType;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.message.WxMaKefuMessage;

/**
 * 文本消息builder.
 */
public final class KefuTextMessageBuilder extends KefuBaseMessageBuilder<KefuTextMessageBuilder> {

  private String content;

  public KefuTextMessageBuilder() {
    this.msgType = KefuMsgType.TEXT;
  }

  public KefuTextMessageBuilder content(String content) {
    this.content = content;
    return this;
  }

  @Override
  public WxMaKefuMessage build() {
    WxMaKefuMessage m = super.build();
    m.setText(new WxMaKefuMessage.KfText(this.content));
    return m;
  }
}
