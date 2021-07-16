package com.yjtech.wisdom.tourism.common.exception;

public enum RealTimeEvnMonitorErrorCode implements IExceptionType {



  //环境监测
  SUCCESS("200", "获取成功"),
  ERROR_DEVICE_NOT_EXIST("400", "设备不存在");



  private String code;
  private String msg;

  private RealTimeEvnMonitorErrorCode(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public String code() {
    return code;
  }

  public String msg() {
    return msg;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return msg;
  }
}
