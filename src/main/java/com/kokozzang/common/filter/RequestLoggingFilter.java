package com.kokozzang.common.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.AbstractRequestLoggingFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

@Slf4j
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {

  private final String beforeMessagePrefix = "before Request [";

  private String beforeMessageSuffix = "]";

  private String afterMessagePrefix = "Request [";

  private String afterMessageSuffix = "]";


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    boolean isFirstRequest = !isAsyncDispatch(request);
    HttpServletRequest requestToUse = request;

    if (isIncludePayload() && isFirstRequest && !(request instanceof ContentCachingRequestWrapper)) {
      requestToUse = new ContentCachingRequestWrapper(request);
    }

    boolean shouldLog = shouldLog(requestToUse);
    if (shouldLog && isFirstRequest) {
//      beforeRequest(requestToUse, getBeforeMessage(requestToUse));
    }
    try {
      filterChain.doFilter(requestToUse, response);
    }
    finally {
      if (shouldLog && !isAsyncStarted(requestToUse)) {
        afterRequest(requestToUse, getAfterMessage(requestToUse));
      }
    }
  }

  @Override
  protected void beforeRequest(HttpServletRequest request, String message) {
    logger.info(message);
  }

  @Override
  protected void afterRequest(HttpServletRequest request, String message) {
    logger.info(message);
  }

  @Override
  protected boolean isIncludePayload() {
    return super.isIncludePayload();
  }

  @Nullable
  protected String getMessagePayload(HttpServletRequest request) {
    ContentCachingRequestWrapper wrapper =
        WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
    if (wrapper != null) {
      byte[] buf = wrapper.getContentAsByteArray();
      if (buf.length > 0) {

        try {
          return new String(buf, wrapper.getCharacterEncoding());
        }

        catch (UnsupportedEncodingException ex) {
          return "[unknown]";
        }
      }
    }
    return null;
  }


  /**
   * Get the message to write to the log before the request.
   * @see #createMessage
   */
  private String getBeforeMessage(HttpServletRequest request) {
    return createMessage(request, this.beforeMessagePrefix, this.beforeMessageSuffix);
  }

  /**
   * Get the message to write to the log after the request.
   * @see #createMessage
   */
  private String getAfterMessage(HttpServletRequest request) {
    return createMessage(request, this.afterMessagePrefix, this.afterMessageSuffix);
  }

  @Override
  public void setMaxPayloadLength(int maxPayloadLength) {
    throw new UnsupportedOperationException("Do not call this method. payload 길이를 계산할 필요 없어서 사용하지 않음.");
  }

  /**
   * Create a log message for the given request, prefix and suffix.
   * <p>If {@code includeQueryString} is {@code true}, then the inner part
   * of the log message will take the form {@code request_uri?query_string};
   * otherwise the message will simply be of the form {@code request_uri}.
   * <p>The final message is composed of the inner part as described and
   * the supplied prefix and suffix.
   */
  @Override
  protected String createMessage(HttpServletRequest request, String prefix, String suffix) {
    RequestMessage requestMessage = new RequestMessage(request);


    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);

    StringBuilder message = new StringBuilder();
    message.append(prefix);
    try {
      message.append(objectMapper.writeValueAsString(requestMessage));
    } catch (JsonProcessingException e) {
      logger.error(e.getMessage(), e);
    }
    message.append(suffix);

    return message.toString();
  }

  @JsonPropertyOrder({"method", "uri", "payload"})
  @JsonInclude(Include.NON_NULL)
  @Getter
  class RequestMessage {

    @JsonIgnore
    private final HttpServletRequest request;

    private String method;

    private String uri;

    private String client;

    private String session;

    private String user;

    private HttpHeaders headers;

    @JsonRawValue
    private String payload;


    public RequestMessage(HttpServletRequest request) {
      this.request = request;
      this.setMethod();
      this.setUri();
      this.setClient();
      this.setSession();
      this.setUser();
      this.setHeaders();
      this.setPayload();
    }

    private void setMethod() {
      this.method = request.getMethod();
    }

    private void setUri() {
      StringBuilder uri = new StringBuilder();
      uri.append(request.getRequestURI());

      if (isIncludeQueryString()) {
        String queryString = request.getQueryString();
        if (queryString != null) {
          uri.append('?').append(queryString);
        }
      }
      this.uri = uri.toString();
    }

    private void setClient() {
      if (isIncludeClientInfo()) {
        String client = request.getRemoteAddr();
        if (StringUtils.hasLength(client)) {
          this.client = client;
        }
      }
    }

    private void setSession() {
      if (isIncludeClientInfo()) {
        HttpSession session = request.getSession(false);
        if (session != null) {
          this.session = session.getId();
        }
      }
    }

    private void setUser() {
      if (isIncludeClientInfo()) {
        String user = request.getRemoteUser();
        if (user != null) {
          this.user = user;
        }
      }
    }

    private void setHeaders() {
      if (isIncludeHeaders()) {
        this.headers = new ServletServerHttpRequest(request).getHeaders();
      }
    }

    private void setPayload() {
      if (isIncludePayload()) {
        String payload = getMessagePayload(request);
        if (payload != null) {
          payload = payload.replaceAll("[\n\t]", "");
          this.payload = payload;
        }
      }
    }
  }

}
