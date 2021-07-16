package com.yjtech.wisdom.tourism.common.exception;

/** Created by yingcan on 2020/3/3 14:09 */
public class CustomException extends RuntimeException {

  protected String code;
  protected String message;

  public CustomException() {
    super();
    this.code = ErrorCode.UNKNOWN.code();
    this.message = ErrorCode.UNKNOWN.msg();
  }

  public CustomException(IExceptionType et) {
    super();
    this.code = et.getCode();
    this.message = et.getMessage();
  }

  public CustomException(Throwable cause) {
    super(cause);
    this.code = ErrorCode.UNKNOWN.code();
    this.message = cause.getMessage();
  }

  public CustomException(String message) {
    super(message);
    this.code = ErrorCode.UNKNOWN.code();
    this.message = message;
  }

  public CustomException(IExceptionType et, String message) {
    super(message);
    this.code = et.getCode();
    this.message = message;
  }

  public CustomException(IExceptionType et, Throwable exp) {
    super(exp);
    this.code = et.getCode();
    this.message = exp.getMessage();
  }

  public CustomException(ErrorCode errorCode, Throwable cause) {
    super(cause);
    this.code = errorCode.code();
    this.message = cause.getMessage();
  }

  public CustomException(ErrorCode errorCode, String message) {
    super(message);
    this.code = errorCode.code();
    this.message = message;
  }

  public CustomException(String code, String message) {
    super(message);
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
