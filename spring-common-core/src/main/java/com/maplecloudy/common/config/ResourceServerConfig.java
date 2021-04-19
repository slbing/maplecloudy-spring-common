package com.maplecloudy.common.config;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maplecloudy.common.oauth2.properties.SecurityProperties;
import com.maplecloudy.common.utils.ResponseUtil;

/**
 * 资源服务器配置
 */
@Configuration
@EnableResourceServer
@ConditionalOnProperty()
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
  @Resource
  private ObjectMapper objectMapper;
  @Resource 
  SecurityProperties securityProperties;
  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
      return (request, response, authException) -> ResponseUtil.responseFailed(objectMapper, response, authException);
  }
  
  /**
   * 处理spring security oauth 处理失败返回消息格式
   */
  @Bean
  public OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler() {
      return new OAuth2AccessDeniedHandler() {

          @Override
          public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
              ResponseUtil.responseFailed(objectMapper, response, authException);
          }
      };
  }
  
  @Bean
  public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
      OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
      expressionHandler.setApplicationContext(applicationContext);
      return expressionHandler;
  }
  @Resource
  public OAuth2WebSecurityExpressionHandler expressionHandler;
  @Resource
  public OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler;
  @Resource
  public AuthenticationEntryPoint authenticationEntryPoint;
  /**
   * 这里设置需要token验证的url 这些url可以在WebSecurityConfigurerAdapter中排查掉，
   * 对于相同的url，如果二者都配置了验证 则优先进入ResourceServerConfigurerAdapter,进行token验证。而不会进行
   * WebSecurityConfigurerAdapter 的 basic auth或表单认证。
   */
  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.stateless(true).expressionHandler(expressionHandler).accessDeniedHandler(oAuth2AccessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint);
  }
  @Override
  public void configure(HttpSecurity http) throws Exception {
    
    ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl = http
        .cors().disable().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
        .headers().frameOptions().disable().and().authorizeRequests()
        .antMatchers(securityProperties.getIgnoreUrls())
        .permitAll().and().authorizeRequests().anyRequest();
    setAuthenticate(authorizedUrl);
    http.oauth2ResourceServer().jwt();
  }
  
  public HttpSecurity setAuthenticate(
      ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl) {
    return authorizedUrl
        .access("@permissionService.hasPermission(request, authentication)")
        .and();
  }
  
}
