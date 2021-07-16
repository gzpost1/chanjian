package com.yjtech.wisdom.tourism.wechat.wxcommon.util;

/**
 * Created by wuyongchong on 2019/9/5.
 */
public class RandomUtils {

  private static final String RANDOM_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  private static final java.util.Random RANDOM = new java.util.Random();

  public static String getRandomStr() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 16; i++) {
      sb.append(RANDOM_STR.charAt(RANDOM.nextInt(RANDOM_STR.length())));
    }
    return sb.toString();
  }
}
