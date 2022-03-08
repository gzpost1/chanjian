package com.yjtech.wisdom.tourism.infrastructure.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wuyongchong on 2019/10/10.
 */
public class IpUtil {

  public static String getIpAddr(HttpServletRequest request) {
    String ipAddress = null;
    try {
      ipAddress = request.getHeader("x-forwarded-for");
      if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
        ipAddress = request.getHeader("Proxy-Client-IP");
      }
      if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
        ipAddress = request.getHeader("WL-Proxy-Client-IP");
      }
      if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
        ipAddress = request.getRemoteAddr();
        if("0:0:0:0:0:0:0:1".equals(ipAddress)){
          ipAddress="127.0.0.1";
        }
      }
      // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
      if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
        // = 15
        if (ipAddress.indexOf(",") > 0) {
          ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }
      }
    } catch (Exception e) {
      ipAddress = "";
    }
    // ipAddress = this.getRequest().getRemoteAddr();

    return ipAddress;
  }
}