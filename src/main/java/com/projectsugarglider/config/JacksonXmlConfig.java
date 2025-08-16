package com.projectsugarglider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Configuration
public class JacksonXmlConfig {

  @Bean
  @Primary
  public ObjectMapper jsonMapper(Jackson2ObjectMapperBuilder builder) {
    return builder
             .createXmlMapper(false)
             .build();
  }

  @Bean
  public XmlMapper xmlMapper(Jackson2ObjectMapperBuilder builder) {
    return builder
             .createXmlMapper(true)
             .build();
  }
}