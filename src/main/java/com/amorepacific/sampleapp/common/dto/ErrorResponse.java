package com.amorepacific.sampleapp.common.dto;

import com.amorepacific.sampleapp.common.exception.CommonException;
import com.amorepacific.sampleapp.common.exception.custom.BadRequestException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.collect.Lists;
import java.util.List;
import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

@Getter
public class ErrorResponse extends Response {

  private static final long serialVersionUID = 1289799327078325796L;

  @JsonInclude(Include.NON_EMPTY)
  private List<ErrorDetail> details;

  public ErrorResponse(CommonException exception) {
    this.code = exception.getCode();
    this.message = exception.getMessage();
  }

  public ErrorResponse(BadRequestException exception) {
    this.code = exception.getCode();
    this.message = exception.getMessage();
    this.details = exception.getDetails();
  }

}
