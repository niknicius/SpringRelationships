package io.nanuvem.manytomany.combobox.demo.exception;

import io.nanuvem.manytomany.combobox.demo.util.TranslatorUtil;
import org.springframework.http.HttpStatus;

public class BusinessException extends Exception {
  private final String message;
  private final String code;
  private final HttpStatus status;

  public BusinessException(String code) {
    this.code = code;
    this.message = TranslatorUtil.getTranslation(code);
    this.status = HttpStatus.BAD_REQUEST;
  }

  public BusinessException(String code, HttpStatus status) {
    this.code = code;
    this.status = status;
    this.message = TranslatorUtil.getTranslation(code);
  }

  public BusinessException(String code, String message) {
    this.code = code;
    this.message = message;
    this.status = HttpStatus.BAD_REQUEST;
  }

  public BusinessException(String code, Exception ex) {
    this.code = code;
    this.message = ex.getMessage();
    this.status = HttpStatus.BAD_REQUEST;
  }

  public BusinessException(Exception ex) {
    this.message = ex.getMessage();
    this.code = ex.getMessage();
    this.status = HttpStatus.BAD_REQUEST;
  }

  public BusinessException(Exception ex, HttpStatus status) {
    this.message = ex.getMessage();
    this.code = ex.getMessage();
    this.status = status;
  }

  public String getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
