package com.kokozzang.common.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
public class ResponseLoggingFilter implements Filter {

  private final String messagePrefix = "Response [";

  private final String messageSuffix = "]";


  @Override
  public void init(FilterConfig filterConfig) {

  }


  @JsonInclude(Include.NON_NULL)
  @JsonPropertyOrder({"status"})
  @Getter
  class ResponseMessage {

    @JsonIgnore
    private final HttpServletResponse response;

    private int status;

    @JsonRawValue
    private String body;

    public ResponseMessage(HttpServletResponse response) {
      this.response = response;
      this.setStatus();
      this.setBody();
    }

    private void setStatus() {
      this.status = response.getStatus();
    }

    private void setBody() {
      String responseBody = new String(((ContentCachingResponseWrapper) response).getContentAsByteArray());
      this.body = responseBody.replaceAll("[\n\t\\s]+", " ");
    }


  }


  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletResponse responseToUse = (HttpServletResponse) response;

    if (!(responseToUse instanceof ContentCachingResponseWrapper)) {
      responseToUse = new ContentCachingResponseWrapper(responseToUse);
    }

    chain.doFilter(request, responseToUse);

    StringBuilder msg = new StringBuilder();
    msg.append(messagePrefix);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);

    ResponseMessage responseMessage = new ResponseMessage(responseToUse);
    msg.append(objectMapper.writeValueAsString(responseMessage));
    msg.append(messageSuffix);

    logger.info(msg.toString());

    ((ContentCachingResponseWrapper)responseToUse).copyBodyToResponse();
  }

  @Override
  public void destroy() {

  }
}
