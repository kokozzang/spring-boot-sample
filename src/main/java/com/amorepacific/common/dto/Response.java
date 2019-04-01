package com.amorepacific.common.dto;

import java.io.Serializable;
import lombok.Getter;


@Getter
class Response implements Serializable {

  private static final long serialVersionUID = 2044753748756796256L;

  String code;

  String message;

}
