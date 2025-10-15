package com.narlock.ollama.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class OllamaCompletion {
  private String model;

  @JsonProperty("created_at")
  private String createdAt;

  private OllamaCompletionMessage message;
  private Boolean done;

  @Data
  @AllArgsConstructor
  @RequiredArgsConstructor
  @Builder
  public static class OllamaCompletionMessage {
    private String role;
    private String content;
  }
}
