package com.yjtech.wisdom.tourism.wechat.wxcommon.util.xml;

/**
 * Created by wuyongchong on 2019/9/5.
 */
public class XStreamMediaIdConverter extends XStreamCDataConverter {
  @Override
  public String toString(Object obj) {
    return "<MediaId>" + super.toString(obj) + "</MediaId>";
  }
}