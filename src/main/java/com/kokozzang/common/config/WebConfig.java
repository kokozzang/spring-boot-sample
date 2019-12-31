package com.kokozzang.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Bean
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {

    ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
        .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
//        .timeZone(TimeZone.getTimeZone("UTC"))
        .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .build();

//    ObjectMapper objectMapper = new ObjectMapper();
//    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
//    objectMapper.setTimeZone(TimeZone.getTimeZone("UTC"));
//

    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);
    converter.setPrettyPrint(true);

    return converter;
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
      }
    };
  }

  @Override
  public void configureContentNegotiation (ContentNegotiationConfigurer configurer) {
    configurer.defaultContentType(MediaType.APPLICATION_JSON);
  }

}
