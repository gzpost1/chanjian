package com.yjtech.wisdom.tourism.wechat.wxopen.util.xml;

import com.thoughtworks.xstream.security.AnyTypePermission;
import com.yjtech.wisdom.tourism.wechat.wxcommon.util.xml.XStreamInitializer;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.message.WxOpenXmlMessage;
import com.thoughtworks.xstream.XStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wuyongchong on 2019/9/5.
 */
public class XStreamTransformer {

  @SuppressWarnings("unchecked")
  public static <T> T fromXml(Class<T> clazz, String xml) {
    return (T) getXStream(clazz).fromXML(xml);
  }

  @SuppressWarnings("unchecked")
  public static <T> T fromXml(Class<T> clazz, InputStream is) {
    return (T) getXStream(clazz).fromXML(is);
  }

  @SuppressWarnings("unchecked")
  public static <T> String toXml(Class<T> clazz, T object) {
    return getXStream(clazz).toXML(object);
  }

  private static <T> XStream getXStream(Class<T> clazz) {
    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(clazz);
    xstream.processAnnotations(getInnerClasses(clazz));
    xstream.addPermission(AnyTypePermission.ANY);
    if (clazz.equals(WxOpenXmlMessage.class)) {
      // 操蛋的微信，模板消息推送成功的消息是MsgID，其他消息推送过来是MsgId
      xstream.aliasField("MsgID", WxOpenXmlMessage.class, "msgId");
    }
    return xstream;
  }

  private static Class<?>[] getInnerClasses(Class<?> clz) {
    Class<?>[] innerClasses = clz.getClasses();
    if (innerClasses == null) {
      return null;
    }
    List<Class<?>> result = new ArrayList<>();
    result.addAll(Arrays.asList(innerClasses));
    for (Class<?> inner : innerClasses) {
      Class<?>[] innerClz = getInnerClasses(inner);
      if (innerClz == null) {
        continue;
      }
      result.addAll(Arrays.asList(innerClz));
    }
    return result.toArray(new Class<?>[0]);
  }
}