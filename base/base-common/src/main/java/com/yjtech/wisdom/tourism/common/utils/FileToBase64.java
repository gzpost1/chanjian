package com.yjtech.wisdom.tourism.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

/**
 * Created by wuyongchong on 2019/12/18.
 */
public class FileToBase64 {

  public static String encodeBase64File(File file) throws Exception {
    if (null == file) {
      return null;
    }
    FileInputStream inputFile = new FileInputStream(file);
    byte[] buffer = new byte[(int) file.length()];
    inputFile.read(buffer);
    inputFile.close();
    return Base64.getEncoder().encodeToString(buffer);
  }
}
