package com.yjtech.wisdom.tourism.common.core.domain;

import com.alibaba.fastjson.JSON;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;

import java.io.Serializable;
import java.util.Date;

/** Created by yingcan on 2019/10/28. */
public class JsonResult<T> implements Serializable {

  /** 错误编号 */
  private String code = "00000";

  /** 信息(如果发生错误，那么代表错误信息) */
  private String message = "";

  /** 返回给客户端的数据对象 */
  private T data;

  /** 响应返回时间 */
  private String time;

  public JsonResult() {
    setTime(DateTimeUtil.dateToString(new Date()));
  }

  public JsonResult(String code) {
    this();
    this.code = code;
  }

  public JsonResult(String code, String message) {
    this();
    this.code = code;
    this.message = message;
  }

  public JsonResult(String code, T data) {
    this();
    this.code = code;
    this.data = data;
  }

  public JsonResult(String code, String message, T data) {
    this();
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public static <T> JsonResult<T> ok() {
    return new JsonResult<T>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage());
  }

  public static <T> JsonResult<T> success() {
    return new JsonResult<T>(ErrorCode.SUCCESS.getCode());
  }

  public static <T> JsonResult<T> success(T data) {
    return new JsonResult<T>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), data);
  }

  public static <T> JsonResult<T> error(String code, String message) {
    return new JsonResult<T>(code, message);
  }

  public static <T> JsonResult<T> error(ErrorCode errorCode) {
    return new JsonResult<T>(errorCode.getCode(), errorCode.getMessage());
  }

  public static <T> JsonResult<T> error(String message) {
    return new JsonResult<T>(ErrorCode.UNKNOWN.getCode(), message);
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
