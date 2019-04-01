package com.amorepacific.common.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ErrorDetail implements Serializable {

  private static final long serialVersionUID = 4196159963085256631L;

  private String field;

  private Object value;

  private String message;


}
