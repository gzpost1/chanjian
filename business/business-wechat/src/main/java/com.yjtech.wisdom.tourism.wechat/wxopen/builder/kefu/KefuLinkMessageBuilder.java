package com.yjtech.wisdom.tourism.wechat.wxopen.builder.kefu;

import com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenConstants.KefuMsgType;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.message.WxMaKefuMessage;

/**
 * 图文链接消息builder
 */
public class KefuLinkMessageBuilder extends KefuBaseMessageBuilder<KefuLinkMessageBuilder> {

  private String title;
  private String description;
  private String url;
  private String thumbUrl;

  public KefuLinkMessageBuilder() {
    this.msgType = KefuMsgType.LINK;
  }

  public KefuLinkMessageBuilder title(String title) {
    this.title = title;
    return this;
  }

  public KefuLinkMessageBuilder description(String description) {
    this.description = description;
    return this;
  }

  public KefuLinkMessageBuilder url(String url) {
    this.url = url;
    return this;
  }

  public KefuLinkMessageBuilder thumbUrl(String thumbUrl) {
    this.thumbUrl = thumbUrl;
    return this;
  }

  @Override
  public WxMaKefuMessage build() {
    WxMaKefuMessage m = super.build();
    m.setLink(WxMaKefuMessage.KfLink.builder().title(this.title)
        .description(this.description)
        .url(this.url)
        .thumbUrl(this.thumbUrl)
        .build()
    );
    return m;
  }
}
