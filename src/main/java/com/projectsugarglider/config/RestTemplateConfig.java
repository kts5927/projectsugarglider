package com.projectsugarglider.config;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        RestTemplate restTemplate = builder
            .uriTemplateHandler(factory)
            .build();

        for (HttpMessageConverter<?> converter : restTemplate.getMessageConverters()) {
            if (converter instanceof MappingJackson2HttpMessageConverter jacksonConv) {
                List<MediaType> types = Arrays.asList(
                    MediaType.APPLICATION_JSON,
                    MediaType.TEXT_PLAIN,                        // text/plain
                    new MediaType("text", "plain", StandardCharsets.UTF_8) // text/plain;charset=UTF-8
                );
                jacksonConv.setSupportedMediaTypes(types);
            }
        }

        return restTemplate;
    }
}
