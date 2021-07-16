package com.yjtech.wisdom.tourism.wechat.wxopen.util.crypto;

import java.security.MessageDigest;

/**
 * Created by wuyongchong on 2019/9/6.
 */
public class DigestUtil {
  private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();

  private DigestUtil() {
  }

  public static String md5Hex(String srcStr) {
    return hash("MD5", srcStr);
  }

  public static String sha1Hex(String srcStr) {
    return hash("SHA-1", srcStr);
  }

  public static String sha256Hex(String srcStr) {
    return hash("SHA-256", srcStr);
  }

  public static String sha384Hex(String srcStr) {
    return hash("SHA-384", srcStr);
  }

  public static String sha512Hex(String srcStr) {
    return hash("SHA-512", srcStr);
  }

  private static String hash(String algorithm, String srcStr) {
    try {
      MessageDigest md = MessageDigest.getInstance(algorithm);
      byte[] bytes = md.digest(srcStr.getBytes("UTF-8"));
      return toHex(bytes);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static String toHex(byte[] bytes) {
    StringBuilder ret = new StringBuilder(bytes.length * 2);
    for (byte b : bytes) {
      ret.append(HEX_DIGITS[(b >> 4) & 0x0f]);
      ret.append(HEX_DIGITS[b & 0x0f]);
    }
    return ret.toString();
  }
}