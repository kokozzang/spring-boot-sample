package com.kokozzang.common.exception.wrapper;

import com.kokozzang.common.exception.CommonException;
import com.kokozzang.common.enums.ExceptionGroup;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerError extends CommonException {

  private static final long serialVersionUID = -7499044607730851701L;


  public InternalServerError() {
    super(ExceptionGroup.INTERNAL_SERVER.getCode(), ExceptionGroup.INTERNAL_SERVER.getMessage());
  }
}
