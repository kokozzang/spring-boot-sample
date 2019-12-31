package com.kokozzang.common.dto;

import lombok.Getter;

@Getter
public class SuccessResponse extends Response {

  private static final long serialVersionUID = 4439783410988091297L;

  private Object data;

  public SuccessResponse(Object data) {
    this.code = "0000";
    this.message = "SUCCESS";
    this.data = data;
  }
}
