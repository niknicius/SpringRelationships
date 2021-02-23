package io.nanuvem.demo.core.dto;

import io.nanuvem.demo.util.TranslatorUtil;

public class RestMessageDTO {
  private String code;
  private String message;

  public RestMessageDTO(String message) {
    this.message = message;
    this.code = TranslatorUtil.UNEXPECTED_ERROR;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
