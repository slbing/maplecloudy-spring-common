package com.maplecloudy.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 通用返回模型
 *
 * @param <T>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultDto {
  private Integer code;
  private String msg;
  public static ResultDto errorOf(Integer code, String message) {
    ResultDto resultDTO = new ResultDto();
    resultDTO.setCode(code);
    resultDTO.setMsg(message);
    return resultDTO;
  }


  public static ResultDto okOf() {
    ResultDto resultDTO = new ResultDto();
    resultDTO.setCode(200);
    resultDTO.setMsg("ok");
    return resultDTO;
  }

  public static ResultDto okOf(String message) {
    ResultDto resultDTO = new ResultDto();
    resultDTO.setCode(200);
    resultDTO.setMsg(message);
    return resultDTO;
  }

  public static ResultDto errorOf(String message) {
    ResultDto resultDTO = new ResultDto();
    resultDTO.setCode(600);
    resultDTO.setMsg(message);
    return resultDTO;
  }

  public static ResultDto resultOf(Integer code, String message) {
    ResultDto resultDTO = new ResultDto();
    resultDTO.setCode(code);
    resultDTO.setMsg(message);
    return resultDTO;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

}

