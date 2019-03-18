package com.amorepacific.sampleapp.common.exception.wrapper;

import com.amorepacific.sampleapp.common.enums.ExceptionGroup;
import com.amorepacific.sampleapp.common.exception.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerError extends CommonException {

  private static final long serialVersionUID = -7499044607730851701L;


  public InternalServerError() {
    super(ExceptionGroup.INTERNAL_SERVER.getCode(), ExceptionGroup.INTERNAL_SERVER.getMessage());
  }
}
