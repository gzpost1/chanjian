package com.yjtech.wisdom.tourism.hotel.constant;

/**
 * 用户状态
 *
 * @author liuhong
 */
public enum HotelEnum {
  LEV_HOTEL("1", "星级酒店"),
  No_LEV_HOTEL("2", "非星级酒店"),
  INN("3", "客栈"),
  PENSION("4", "民宿");

  private final String value;
  private final String name;

  HotelEnum(String value, String name) {
    this.value = value;
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public String getName() {
    return name;
  }
}
