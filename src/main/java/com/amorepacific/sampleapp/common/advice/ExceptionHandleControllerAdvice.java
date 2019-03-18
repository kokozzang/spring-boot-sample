package com.amorepacific.sampleapp.common.advice;

import com.amorepacific.sampleapp.common.dto.ErrorDetail;
import com.amorepacific.sampleapp.common.dto.ErrorResponse;
import com.amorepacific.sampleapp.common.exception.CommonException;
import com.amorepacific.sampleapp.common.exception.wrapper.BadRequest;
import com.amorepacific.sampleapp.common.exception.wrapper.MethodNotAllowed;
import com.amorepacific.sampleapp.common.exception.wrapper.NotFoundException;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;


@RestControllerAdvice
public class ExceptionHandleControllerAdvice {

  @ExceptionHandler(value = CommonException.class)
  public ResponseEntity<ErrorResponse> handleCommonException(CommonException e) {
    return this.sendError(e, new ErrorResponse(e));
  }

  // 400 Bad Request
  // https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/400
  // https://tools.ietf.org/html/rfc7231#section-6.5.4
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleBadRequestException(MethodArgumentNotValidException e) {
    List<ErrorDetail> errorDetails = Lists.newArrayList();

    e.getBindingResult().getAllErrors().forEach(error -> {
      FieldError fieldError = (FieldError) error;
      ErrorDetail errorDetail = ErrorDetail.builder()
          .field(fieldError.getField())
          .value(fieldError.getRejectedValue())
          .message(fieldError.getDefaultMessage())
          .build();
      errorDetails.add(errorDetail);
    });

    CommonException commonException = new BadRequest();

    return this.sendError(commonException, new ErrorResponse(commonException, errorDetails));
  }


  /**
   * 404 Not Found
   * https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/400
   * https://tools.ietf.org/html/rfc7231#section-6.5.1
   */
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(Exception e) {
    CommonException commonException = new NotFoundException();
    return this.sendError(commonException, new ErrorResponse(commonException));
  }

  /**
   * 405 Method Not Allowed
   * https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/405
   * https://tools.ietf.org/html/rfc7231#section-6.5.5
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
    CommonException commonException = new MethodNotAllowed();
    return this.sendError(commonException, new ErrorResponse(commonException));
  }


//  @ExceptionHandler(value = Exception.class)
//  public ResponseEntity<ErrorResponse> handleException(Exception e) {
//    CommonException commonException = new InternalServerError();
//    return this.sendError(commonException, new ErrorResponse(commonException));
//  }

  private ResponseEntity<ErrorResponse> sendError(CommonException e, final ErrorResponse errorResponse) {
    return ResponseEntity
        .status(e.getClass().getAnnotation(ResponseStatus.class).code())
        .body(errorResponse);
  }

}
