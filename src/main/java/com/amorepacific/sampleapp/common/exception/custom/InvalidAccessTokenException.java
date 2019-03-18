package com.amorepacific.sampleapp.common.exception.custom;

import com.amorepacific.sampleapp.common.enums.ExceptionGroup;
import com.amorepacific.sampleapp.common.exception.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class InvalidAccessTokenException extends CommonException {

  private static final long serialVersionUID = -3020588571378089083L;

  public InvalidAccessTokenException() {
    super(ExceptionGroup.INVALID_ACCESS_TOKEN.getCode(), ExceptionGroup.INVALID_ACCESS_TOKEN.getMessage());
  }

}
