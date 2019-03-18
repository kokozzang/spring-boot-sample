package com.amorepacific.sampleapp.common.enums;

import lombok.Getter;

public enum ExceptionGroup {
  INTERNAL_SERVER("500","Internal Server Error Exception"),
  BAD_REQUEST("400", "Invalid parameters"),
  NOT_FOUND("404", "Not Found"),
  METHOD_NOT_ALLOWED("405", "Requested method is not supported"),
  // Authorize Exception(1000 ~ 1099)
  INVALID_ACCESS_TOKEN("1000","Invalid Access Token"),
  INVALID_REFRESH_TOKEN("1001","Invalid Refresh Token"),
  INVALID_AUTHORIZATION_CODE("1002","Invalid Authorization Code"),
  // Member Exception(1100 ~ 1199)
  UNKNOWN_MEMBER("1100","Unknown Member"),
  SLEEP_MEMBER("1101","Sleep Member"),
  // Client Exception(1200 ~ 1299)
  UNAUTHORIZED_CLIENT("1200", "Unauthorized Client"),
  UNKNOWN_CLIENT("1201","Unknown Client"),
  // Common Exception(9990 ~ 9999)
  ;

  @Getter
  private String code;

  @Getter
  private String message;

  ExceptionGroup(String code, String message){
    this.code = code;
    this.message = message;
  }
}
