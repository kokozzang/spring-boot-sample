package com.amorepacific.common.exception.custom;

import com.amorepacific.common.dto.ErrorDetail;
import com.amorepacific.common.exception.wrapper.BadRequest;
import com.google.common.collect.Lists;
import java.util.List;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends BadRequest {

  private static final long serialVersionUID = 3277350655053168054L;

  @Getter
  private Exception exception;

  @Getter
  private List<ErrorDetail> details = Lists.newArrayList();

  private BadRequestException() {

  }



  public BadRequestException(List<ErrorDetail> errorDetails) {
    this.exception = new BadRequest();
    this.details = errorDetails;
  }

  public BadRequestException(Exception exception) throws Exception {

    if (exception instanceof BadRequestException) {
      this.handleBadRequest((BadRequestException) exception);
    }
    else if (exception instanceof MethodArgumentNotValidException) {
      this.handleBadRequest((MethodArgumentNotValidException) exception);
    }
    else if (exception instanceof MethodArgumentTypeMismatchException) {
      this.handleBadRequest((MethodArgumentTypeMismatchException) exception);
    }
    else if (exception instanceof MissingServletRequestParameterException) {
      this.handleBadRequest((MissingServletRequestParameterException) exception);
    }
    else if (exception instanceof HttpMessageNotReadableException) {
      this.handleBadRequest((HttpMessageNotReadableException) exception);
    }
    else {
      throw exception;
    }

  }

  /**
   * 400 Bad Request
   * 커스텀 밸리데이션 에러 핸들링
   * https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/400
   * https://tools.ietf.org/html/rfc7231#section-6.5.4
   * @param exception
   */
  private void handleBadRequest(BadRequestException exception) {
    this.exception = exception;
    this.details = exception.getDetails();
  }

  /**
   * bean vadlidation
   *  @param exception bean validation으로 발생한 exception 객체
   */
  private void handleBadRequest(MethodArgumentNotValidException exception) {
    this.exception = exception;

    List<ErrorDetail> errorDetails = Lists.newArrayList();

    exception.getBindingResult().getAllErrors().forEach(error -> {
      FieldError fieldError = (FieldError) error;
      ErrorDetail errorDetail = ErrorDetail.builder()
          .field(fieldError.getField())
          .value(fieldError.getRejectedValue())
          .message(fieldError.getDefaultMessage())
          .build();
      errorDetails.add(errorDetail);
    });

    this.details = errorDetails;
  }

  /**
   * query string parameter 타입 오류
   * @param exception query string 파라미터 바인딩시 발생한 exception 객체
   */
  private void handleBadRequest(MethodArgumentTypeMismatchException exception) {
    this.exception = exception;
    this.message = "Type mismatch for \'" + exception.getName() + "\'";
  }

  /**
   * 필수 query string parameter 누락
   * @param exception query string 파라미터 바인딩시 발생한 exception 객체
   */
  private void handleBadRequest(MissingServletRequestParameterException exception) {
    this.exception = exception;
    this.message = "Required parameter \'" + exception.getParameterName() + "\' is not present";
  }

  /**
   * json 파싱 에러
   * @param exception
   */
  private void handleBadRequest(HttpMessageNotReadableException exception) {
    this.exception = exception;
    this.message = "JSON parse error";
  }

}
