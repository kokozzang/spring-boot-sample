package com.amorepacific.sampleapp.common.exception.wrapper;

import com.amorepacific.sampleapp.common.enums.ExceptionGroup;
import com.amorepacific.sampleapp.common.exception.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequest extends CommonException {

  private static final long serialVersionUID = -7178939416980613187L;

  public BadRequest() {
    super(ExceptionGroup.BAD_REQUEST.getCode(), ExceptionGroup.BAD_REQUEST.getMessage());
  }

}
