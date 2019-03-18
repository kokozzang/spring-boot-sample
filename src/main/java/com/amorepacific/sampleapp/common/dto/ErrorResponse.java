package com.amorepacific.sampleapp.common.dto;

import com.amorepacific.sampleapp.common.exception.CommonException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Collections;
import java.util.List;
import lombok.Getter;

@Getter
public class ErrorResponse extends Response {

  private static final long serialVersionUID = 1289799327078325796L;

  @JsonInclude(Include.NON_EMPTY)
  private List<ErrorDetail> details = Collections.emptyList();

  public ErrorResponse(CommonException exception) {
    this.code = exception.getCode();
    this.message = exception.getMessage();
  }

  public ErrorResponse(CommonException exception, List<ErrorDetail> errorDetails) {
    this(exception);
    this.details = errorDetails;
  }

}
