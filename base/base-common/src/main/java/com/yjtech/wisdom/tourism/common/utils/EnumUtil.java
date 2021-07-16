package com.yjtech.wisdom.tourism.common.utils;

import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.constant.EnumInterface;

import java.util.List;

/**
 * Created by wuyongchong on 2019/10/11.
 */
public class EnumUtil {

  public static <T extends EnumInterface> T getEnumItem(Class<T> clzz, Object value) {
    if (null == value) {
      return null;
    }
    T[] enumOjects = clzz.getEnumConstants();
    for (T obj : enumOjects) {
      if (obj.getValue().equals(value)) {
        return obj;
      }
    }
    return null;
  }

  public static <T extends EnumInterface> List<Object> getEnumValues(Class<T> clzz) {
    List<Object> enumValues = Lists.newArrayList();
    T[] enumOjects = clzz.getEnumConstants();
    for (T obj : enumOjects) {
      enumValues.add(obj.getValue());
    }
    return enumValues;
  }

}
