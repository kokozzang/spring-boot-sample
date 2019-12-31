package com.kokozzang.common.dto;

import com.kokozzang.common.exception.CommonException;
import com.kokozzang.common.exception.custom.BadRequestException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Getter;

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
