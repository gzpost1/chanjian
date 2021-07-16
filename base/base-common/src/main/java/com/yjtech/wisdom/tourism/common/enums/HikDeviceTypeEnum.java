package com.yjtech.wisdom.tourism.common.enums;

import java.util.Objects;

/**
 * 用户状态
 *
 * @author Mujun~
 */
public enum HikDeviceTypeEnum {

  CAMERA("1", "监控"),
  DB("2", "单兵"),
  SHIP("3", "船载"),
  ALARM("4","报警");

  private final String type;
  private final String desc;

  HikDeviceTypeEnum(String type, String desc) {
    this.type = type;
    this.desc = desc;
  }

  public String getType() {
    return type;
  }

  public String getDesc() {
    return desc;
  }


    // 普通方法
    public static String getType(String name) {
        for (HikDeviceTypeEnum c : HikDeviceTypeEnum.values()) {
      if (Objects.equals(c.getDesc(),name)) {
                return c.getType();
            }
        }
        return null;
    }
}
