package io.nanuvem.demo.core.config;

import io.nanuvem.demo.core.dto.RestMessageDTO;
import io.nanuvem.demo.exception.BusinessException;
import io.nanuvem.demo.util.TranslatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  Logger logger = LoggerFactory.getLogger(this.getClass());

  @ExceptionHandler(BusinessException.class)
  public final ResponseEntity<RestMessageDTO> handleBusinessException(
    BusinessException ex,
    WebRequest request
  ) {
    this.logger.error(ex.getMessage());
    RestMessageDTO dto = new RestMessageDTO(ex.getMessage());
    dto.setCode(ex.getCode());
    return ResponseEntity.status(ex.getStatus()).body(dto);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<RestMessageDTO> handleException(Exception ex) {
    this.logger.error(ex.getMessage());
    RestMessageDTO dto = new RestMessageDTO(ex.getMessage());
    dto.setCode(TranslatorUtil.UNEXPECTED_ERROR);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
  }
}
