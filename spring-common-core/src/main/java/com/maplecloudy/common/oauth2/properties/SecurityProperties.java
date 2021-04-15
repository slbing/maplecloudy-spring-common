package com.maplecloudy.common.oauth2.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@ConfigurationProperties(prefix = "maplecloudy.security")
@RefreshScope
public class SecurityProperties {
  /**
   * 配置只进行登录认证，不进行url权限认证的api 所有已登录的人都能访问的api
   */
  private String[] ignoreUrls = {};
  
  public String[] getIgnoreUrls() {
    return ignoreUrls;
  }
  
  public void setIgnoreUrls(String[] ignoreUrls) {
    this.ignoreUrls = ignoreUrls;
  }
}
