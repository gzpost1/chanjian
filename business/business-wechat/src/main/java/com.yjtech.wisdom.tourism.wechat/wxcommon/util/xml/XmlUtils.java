package com.yjtech.wisdom.tourism.wechat.wxcommon.util.xml;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultText;

/**
 * XML转换工具类. Created by wuyongchong on 2019/9/5.
 */
public class XmlUtils {

  public static Map<String, Object> xml2Map(String xmlString) {
    Map<String, Object> map = new HashMap<>(16);
    try {
      SAXReader saxReader = new SAXReader();
      Document doc = saxReader.read(new StringReader(xmlString));
      Element root = doc.getRootElement();
      List<Element> elements = root.elements();
      for (Element element : elements) {
        map.put(element.getName(), element2MapOrString(element));
      }
    } catch (DocumentException e) {
      throw new RuntimeException(e);
    }

    return map;
  }

  private static Object element2MapOrString(Element element) {
    Map<String, Object> result = Maps.newHashMap();

    final List<Node> content = element.content();
    if (content.size() <= 1) {
      return element.getText();
    }

    final Set<String> names = names(content);
    if (names.size() == 1) {
      // 说明是个列表，各个子对象是相同的name
      List<Object> list = Lists.newArrayList();
      for (Node node : content) {
        if (node instanceof DefaultText) {
          continue;
        }

        if (node instanceof Element) {
          list.add(element2MapOrString((Element) node));
        }
      }

      result.put(names.iterator().next(), list);
    } else {
      for (Node node : content) {
        if (node instanceof DefaultText) {
          continue;
        }

        if (node instanceof Element) {
          result.put(node.getName(), element2MapOrString((Element) node));
        }
      }
    }

    return result;
  }

  private static Set<String> names(List<Node> nodes) {
    Set<String> names = Sets.newHashSet();
    for (Node node : nodes) {
      if (node instanceof DefaultText) {
        continue;
      }
      names.add(node.getName());
    }

    return names;
  }

  /**
   * 转换不带CDDATA的XML
   *
   * @
   */
  private static XStream getXStream() {
    // 实例化XStream基本对象
    XStream xstream = new XStream(new DomDriver(StandardCharsets.UTF_8.name(), new NoNameCoder() {
      // 不对特殊字符进行转换，避免出现重命名字段时的“双下划线”
      @Override
      public String encodeNode(String name) {
        return name;
      }
    }));
    // 忽视XML与JAVABEAN转换时，XML中的字段在JAVABEAN中不存在的部分
    xstream.ignoreUnknownElements();
    return xstream;
  }

  /**
   * 转换带CDDATA的XML
   *
   * @
   */
  private static XStream getXStreamWithCData(List<String> ignoreCDATA) {
    // 实例化XStream扩展对象
    XStream xstream = new XStream(new XppDriver() {
      // 扩展xstream，使其支持CDATA块
      @Override
      public HierarchicalStreamWriter createWriter(Writer out) {
        return new PrettyPrintWriter(out) {
          boolean cdata = true;

          // 不对特殊字符进行转换，避免出现重命名字段时的“双下划线”
          @Override
          public String encodeNode(String name) {
            if (!ignoreCDATA.isEmpty()) {
              for (String str : ignoreCDATA) {
                if (str.equals(name)) {
                  cdata = false;
                  return name;
                }
              }
            }
            cdata = true;
            return name;
          }

          // 对xml节点的转换都增加CDATA标记
          @Override
          protected void writeText(QuickWriter writer, String text) {
            if (cdata) {
              writer.write("<![CDATA[");
              writer.write(text);
              writer.write("]]>");
            } else {
              writer.write(text);
            }
          }


        };
      }
    });
    // 忽视XML与JAVABEAN转换时，XML中的字段在JAVABEAN中不存在的部分
    xstream.ignoreUnknownElements();
    return xstream;
  }

  /**
   * 以压缩的方式输出XML
   */
  public static String toCompressXml(Object obj) {
    XStream xstream = getXStream();
    StringWriter sw = new StringWriter();
    // 识别obj类中的注解
    xstream.processAnnotations(obj.getClass());
    // 设置JavaBean的类别名
    xstream.aliasType("xml", obj.getClass());
    xstream.marshal(obj, new CompactWriter(sw));
    return sw.toString();
  }

  /**
   * 以格式化的方式输出XML
   */
  public static String toXml(Object obj) {
    XStream xstream = getXStream();
    // 识别obj类中的注解
    xstream.processAnnotations(obj.getClass());
    // 设置JavaBean的类别名
    xstream.aliasType("xml", obj.getClass());
    return xstream.toXML(obj);
  }

  /**
   * 转换成JavaBean
   */
  @SuppressWarnings("unchecked")
  public static <T> T toBean(String xmlStr, Class<T> cls) {
    XStream xstream = getXStream();
    // 识别cls类中的注解
    xstream.processAnnotations(cls);
    // 设置JavaBean的类别名
    xstream.aliasType("xml", cls);
    T t = (T) xstream.fromXML(xmlStr);
    return t;
  }

  /**
   * 以格式化的方式输出XML
   */
  public static String toXmlWithCData(Object obj, List<String> ignoreCDAA) {
    XStream xstream = getXStreamWithCData(ignoreCDAA);
    // 识别obj类中的注解
    xstream.processAnnotations(obj.getClass());
    // 设置JavaBean的类别名
    xstream.aliasType("xml", obj.getClass());
    return xstream.toXML(obj);
  }

  /**
   * 转换成JavaBean
   */
  @SuppressWarnings("unchecked")
  public static <T> T toBeanWithCData(String xmlStr, Class<T> cls) {
    XStream xstream = getXStreamWithCData(null);
    // 识别cls类中的注解
    xstream.processAnnotations(cls);
    // 设置JavaBean的类别名
    xstream.alias("xml", cls);
    T t = (T) xstream.fromXML(xmlStr);
    return t;
  }
}