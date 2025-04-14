package com.habitquest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// ObjectMapper를 싱글톤으로 사용하기 위한 Bean 등록
// objectMapper는 객체와 JSON 간 변환을 담당함
@Configuration
public class ObjectMapperConfig {
  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

}
