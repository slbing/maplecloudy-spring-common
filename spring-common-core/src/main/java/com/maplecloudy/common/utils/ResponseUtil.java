package com.maplecloudy.common.utils;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maplecloudy.common.model.ResultDto;

/**
 * @author kim
 * @date 2021/03/23
 */
public class ResponseUtil {
  public static final Log LOG = LogFactory.getLog(ResponseUtil.class);

  private ResponseUtil() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * 通过流写到前端
   *
   * @param objectMapper 对象序列化
   * @param response
   * @param msg          返回信息
   * @param httpStatus   返回状态码
   * @throws IOException
   */
  public static void responseWriter(ObjectMapper objectMapper,
      HttpServletResponse response, String msg, int httpStatus)
      throws IOException {

    responseWrite(objectMapper, response, HttpStatus.valueOf(httpStatus),
        ResultDto.errorOf(httpStatus, msg));
  }

  /**
   * 通过流写到前端
   *
   * @param objectMapper 对象序列化
   * @param response
   */
  public static void responseSucceed(ObjectMapper objectMapper,
      HttpServletResponse response, String msg) throws IOException {
    responseWrite(objectMapper, response, HttpStatus.OK, ResultDto.okOf(msg));
  }

  /**
   * 通过流写到前端
   *
   * @param objectMapper
   * @param response
   * @throws IOException
   */
  public static void responseFailed(ObjectMapper objectMapper,
      HttpServletResponse response, Exception ex) throws IOException {
    LOG.error(ExceptionUtils.getStackTrace(ex));
    if (ex instanceof AccessDeniedException) {
      responseWrite(objectMapper, response, HttpStatus.FORBIDDEN,
          ResultDto.errorOf(HttpStatus.FORBIDDEN.value(), ex.getMessage()));
    } else {
      responseWrite(objectMapper, response, HttpStatus.UNAUTHORIZED,
          ResultDto.errorOf(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()));
    }

  }

  private static void responseWrite(ObjectMapper objectMapper,
      HttpServletResponse response, HttpStatus httpStatus, ResultDto result)
      throws IOException {
    response.setStatus(httpStatus.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    try (Writer writer = response.getWriter()) {
      writer.write(objectMapper.writeValueAsString(result));
      writer.flush();
    }
  }
}
