package com.yjtech.wisdom.tourism.wechat.wxopen.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wuyongchong on 2019/9/7.
 */
public class FileUtils {

  /**
   * 创建临时文件.
   *
   * @param inputStream 输入文件流
   * @param name 文件名
   * @param ext 扩展名
   * @param tmpDirFile 临时文件夹目录
   */
  public static File createTmpFile(InputStream inputStream, String name, String ext,
      File tmpDirFile) throws IOException {
    File resultFile = File.createTempFile(name, '.' + ext, tmpDirFile);
    resultFile.deleteOnExit();
    org.apache.commons.io.FileUtils.copyToFile(inputStream, resultFile);
    return resultFile;
  }
}
