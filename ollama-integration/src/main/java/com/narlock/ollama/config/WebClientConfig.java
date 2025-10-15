package com.narlock.ollama.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  @Value("${ollama.url}")
  private String ollamaUrl;

  @Bean
  public WebClient ollamaWebClient() {
    return WebClient.builder()
        .baseUrl(ollamaUrl)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }
}
