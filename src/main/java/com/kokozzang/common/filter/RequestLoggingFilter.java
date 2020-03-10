package com.kokozzang.common.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.AbstractRequestLoggingFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

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
      requestToUse = new ContentCachingRequestWrapper(request, getMaxPayloadLength());
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

  /**
   * Create a log message for the given request, prefix and suffix.
   * <p>If {@code includeQueryString} is {@code true}, then the inner part
   * of the log message will take the form {@code request_uri?query_string};
   * otherwise the message will simply be of the form {@code request_uri}.
   * <p>The final message is composed of the inner part as described and
   * the supplied prefix and suffix.
   */
  protected String createMessage(HttpServletRequest request, String prefix, String suffix) {
    StringBuilder msg = new StringBuilder();
    msg.append(prefix);
    msg.append("uri=").append(request.getRequestURI());

    if (isIncludeQueryString()) {
      String queryString = request.getQueryString();
      if (queryString != null) {
        msg.append('?').append(queryString);
      }
    }

    if (isIncludeClientInfo()) {
      String client = request.getRemoteAddr();
      if (StringUtils.hasLength(client)) {
        msg.append(";client=").append(client);
      }
      HttpSession session = request.getSession(false);
      if (session != null) {
        msg.append(";session=").append(session.getId());
      }
      String user = request.getRemoteUser();
      if (user != null) {
        msg.append(";user=").append(user);
      }
    }

    if (isIncludeHeaders()) {
      msg.append(";headers=").append(new ServletServerHttpRequest(request).getHeaders());
    }

    if (isIncludePayload()) {
      String payload = getMessagePayload(request);
      if (payload != null) {
        payload = payload.replaceAll("[\n\t]", "");
        msg.append(";payload=").append(payload);
      }
    }

    msg.append(suffix);
    return msg.toString();
  }

}
