package com.yjtech.wisdom.tourism.wechat.wxopen.builder.kefu;

import com.yjtech.wisdom.tourism.wechat.wxopen.bean.message.WxMaKefuMessage;

public class KefuBaseMessageBuilder<T> {

  protected String msgType;
  protected String toUser;

  @SuppressWarnings("unchecked")
  public T toUser(String toUser) {
    this.toUser = toUser;
    return (T) this;
  }

  /**
   * 构造器方法.
   */
  public WxMaKefuMessage build() {
    WxMaKefuMessage m = new WxMaKefuMessage();
    m.setMsgType(this.msgType);
    m.setToUser(this.toUser);
    return m;
  }
}
