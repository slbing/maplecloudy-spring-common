package com.maplecloudy.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

public class AddrUtil {
  public static final Log LOG = LogFactory.getLog(AddrUtil.class);
  private final static String UNKNOWN_STR = "unknown";
  
  /**
   * 获取客户端IP地址
   */
  public static String getRemoteAddr(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");
    if (isEmptyIP(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
      if (isEmptyIP(ip)) {
        ip = request.getHeader("WL-Proxy-Client-IP");
        if (isEmptyIP(ip)) {
          ip = request.getHeader("HTTP_CLIENT_IP");
          if (isEmptyIP(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            if (isEmptyIP(ip)) {
              ip = request.getRemoteAddr();
              if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                // 根据网卡取本机配置的IP
                ip = getLocalAddr();
              }
            }
          }
        }
      }
    } else if (ip.length() > 15) {
      String[] ips = ip.split(",");
      for (int index = 0; index < ips.length; index++) {
        String strIp = ips[index];
        if (!isEmptyIP(ip)) {
          ip = strIp;
          break;
        }
      }
    }
    return ip;
  }
  
  private static boolean isEmptyIP(String ip) {
    if (StringUtils.isEmpty(ip) || UNKNOWN_STR.equalsIgnoreCase(ip)) {
      return true;
    }
    return false;
  }
  
  /**
   * 获取本机的IP地址
   */
  public static String getLocalAddr() {
    try {
      return InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      LOG.error("InetAddress.getLocalHost()-error", e);
    }
    return "";
  }
}
