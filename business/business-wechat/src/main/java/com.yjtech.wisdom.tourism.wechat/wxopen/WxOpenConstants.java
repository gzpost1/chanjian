package com.yjtech.wisdom.tourism.wechat.wxopen;

/**
 * <pre>
 *  小程序常量.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class WxOpenConstants {

  /**
   * 微信推送过来的消息的类型，和发送给微信xml格式消息的消息类型.
   */
  public static class XmlMsgType {

    public static final String TEXT = "text";
    public static final String IMAGE = "image";
    public static final String VOICE = "voice";
  }

  /**
   * 客服消息的消息类型.
   */
  public static class KefuMsgType {

    /**
     * 文本消息.
     */
    public static final String TEXT = "text";
    /**
     * 图片消息.
     */
    public static final String IMAGE = "image";
    /**
     * 图文链接.
     */
    public static final String LINK = "link";
    /**
     * 小程序卡片消息.
     */
    public static final String MA_PAGE = "miniprogrampage";
  }

  public static final class ErrorCode {

    /**
     * 40001 获取access_token时AppSecret错误，或者access_token无效.
     */
    public static final int ERR_40001 = 40001;

    /**
     * 42001 access_token超时.
     */
    public static final int ERR_42001 = 42001;

    /**
     * 40014 不合法的access_token，请开发者认真比对access_token的有效性（如是否过期）.
     */
    public static final int ERR_40014 = 40014;
  }
}
