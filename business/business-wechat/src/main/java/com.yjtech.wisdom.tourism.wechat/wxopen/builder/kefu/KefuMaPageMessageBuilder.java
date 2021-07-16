package com.yjtech.wisdom.tourism.wechat.wxopen.builder.kefu;

import com.yjtech.wisdom.tourism.wechat.wxopen.WxOpenConstants.KefuMsgType;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.message.WxMaKefuMessage;

/**
 * 小程序卡片消息builder
 */
public class KefuMaPageMessageBuilder extends KefuBaseMessageBuilder<KefuMaPageMessageBuilder> {

  private String title;
  private String pagePath;
  private String thumbMediaId;

  public KefuMaPageMessageBuilder() {
    this.msgType = KefuMsgType.MA_PAGE;
  }

  public KefuMaPageMessageBuilder title(String title) {
    this.title = title;
    return this;
  }

  public KefuMaPageMessageBuilder pagePath(String pagePath) {
    this.pagePath = pagePath;
    return this;
  }

  public KefuMaPageMessageBuilder thumbMediaId(String thumbMediaId) {
    this.thumbMediaId = thumbMediaId;
    return this;
  }

  @Override
  public WxMaKefuMessage build() {
    WxMaKefuMessage m = super.build();
    m.setMaPage(WxMaKefuMessage.KfMaPage.builder()
        .title(this.title)
        .pagePath(this.pagePath)
        .thumbMediaId(this.thumbMediaId)
        .build()
    );
    return m;
  }
}
