package com.kokozzang.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
public class ResponseLoggingFilter implements Filter {

  private final String messagePrefix = "Response [";

  private final String messageSuffix = "]";



  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

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
    msg.append("status=").append(responseToUse.getStatus());

    String responseBody = new String(((ContentCachingResponseWrapper) responseToUse).getContentAsByteArray());
    responseBody = responseBody.replaceAll("[\n\t]", "");
    msg.append(";body=").append(responseBody);

    msg.append(messageSuffix);

    logger.info(msg.toString());

    ((ContentCachingResponseWrapper)responseToUse).copyBodyToResponse();
  }

  @Override
  public void destroy() {

  }
}
