package com.kokozzang.common.config;


import com.kokozzang.common.filter.CorrelationFilter;
import com.kokozzang.common.filter.RequestLoggingFilter;
import com.kokozzang.common.filter.ResponseLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AppConfig {

  @Bean
  public FilterRegistrationBean<CorrelationFilter> correlationFilter() {
    FilterRegistrationBean<CorrelationFilter> filterRegistrationBean = new FilterRegistrationBean<>();

    CorrelationFilter requestLoggingFilter = new CorrelationFilter();

    filterRegistrationBean.setFilter(requestLoggingFilter);
    filterRegistrationBean.addUrlPatterns("/product/*");
    filterRegistrationBean.setOrder(1);

    return filterRegistrationBean;
  }

  @Bean
  public FilterRegistrationBean<RequestLoggingFilter> requestLoggingFilter() {
    FilterRegistrationBean<RequestLoggingFilter> filterRegistrationBean = new FilterRegistrationBean<>();

    RequestLoggingFilter requestLoggingFilter = new RequestLoggingFilter();
    requestLoggingFilter.setIncludeHeaders(true);
    requestLoggingFilter.setIncludeQueryString(true);
    requestLoggingFilter.setIncludePayload(true);
    requestLoggingFilter.setMaxPayloadLength(100);
    requestLoggingFilter.setIncludeClientInfo(true);

    filterRegistrationBean.setFilter(requestLoggingFilter);

    filterRegistrationBean.addUrlPatterns("/product/*");

    return filterRegistrationBean;
  }

  @Bean
  public FilterRegistrationBean<ResponseLoggingFilter> responseLoggingFilter() {
    FilterRegistrationBean<ResponseLoggingFilter> filterRegistrationBean = new FilterRegistrationBean<>();

    ResponseLoggingFilter responseLoggingFilter = new ResponseLoggingFilter();
    filterRegistrationBean.setFilter(responseLoggingFilter);
    filterRegistrationBean.setOrder(9999);
    filterRegistrationBean.addUrlPatterns("/product/*");

    return filterRegistrationBean;
  }


}
