package com.amorepacific.sampleapp.common.exception.wrapper;

import com.amorepacific.sampleapp.common.enums.ExceptionGroup;
import com.amorepacific.sampleapp.common.exception.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFound extends CommonException {

  private static final long serialVersionUID = 4978705713496992731L;

  public NotFound() {
    super(ExceptionGroup.NOT_FOUND.getCode(), ExceptionGroup.NOT_FOUND.getMessage());
  }
}
