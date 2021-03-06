package com.kokozzang.common.exception.wrapper;

import com.kokozzang.common.exception.CommonException;
import com.kokozzang.common.enums.ExceptionGroup;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
public class MethodNotAllowed extends CommonException {

  private static final long serialVersionUID = 5601568807025476346L;


  public MethodNotAllowed() {
    super(ExceptionGroup.METHOD_NOT_ALLOWED.getCode(), ExceptionGroup.METHOD_NOT_ALLOWED.getMessage());
  }
}
