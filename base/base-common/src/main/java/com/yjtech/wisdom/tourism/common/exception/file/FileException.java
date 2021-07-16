package com.yjtech.wisdom.tourism.common.exception.file;

import com.yjtech.wisdom.tourism.common.exception.BaseException;

/**
 * 文件信息异常类
 *
 * @author liuhong
 */
public class FileException extends BaseException {
  private static final long serialVersionUID = 1L;

  public FileException(String code, Object[] args) {
    super("file", code, args, null);
  }
}
