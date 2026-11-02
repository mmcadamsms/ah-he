package com.ahhe.webapi.operations;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Type;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class OperationsJsonConfiguration implements WebMvcConfigurer {
  private final ObjectMapper mapper;

  public OperationsJsonConfiguration(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.addFirst(
        new MappingJackson2HttpMessageConverter(
            mapper
                .copy()
                .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .enable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)
                .enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)) {
          @Override
          public boolean canRead(Type type, Class<?> context, MediaType mediaType) {
            return context == OperationsController.class && super.canRead(type, context, mediaType);
          }

          @Override
          public boolean canRead(Class<?> type, MediaType mediaType) {
            return type == OperationsController.ContextInput.class
                && super.canRead(type, mediaType);
          }

          @Override
          public boolean canWrite(Class<?> type, MediaType mediaType) {
            return false;
          }
        });
  }
}
