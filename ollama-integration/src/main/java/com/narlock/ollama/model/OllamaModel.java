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
public class OllamaModel {
  private String name;

  @JsonProperty("modified_at")
  private String modifiedAt;

  private String digest;
  private OllamaModelDetails details;

  @Data
  @AllArgsConstructor
  @RequiredArgsConstructor
  @Builder
  public static class OllamaModelDetails {
    private String format;
    private String family;

    @JsonProperty("parameter_size")
    private String parameterSize;

    @JsonProperty("quantization_level")
    private String quantizationLevel;
  }
}
