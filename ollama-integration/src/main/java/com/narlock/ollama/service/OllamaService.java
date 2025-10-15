package com.narlock.ollama.service;

import com.narlock.ollama.model.MessageInput;
import com.narlock.ollama.model.OllamaCompletion;
import com.narlock.ollama.model.OllamaListResponse;
import com.narlock.ollama.model.OllamaModel;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class OllamaService {
  private final WebClient webClient;

  public Mono<List<OllamaModel>> listModels() {
    return webClient
        .get()
        .uri("/api/tags")
        .retrieve()
        .bodyToMono(OllamaListResponse.class)
        .map(OllamaListResponse::getModels);
  }

  public Mono<OllamaCompletion> chatRequest(String model, List<MessageInput> messages) {
    Map<String, Object> payload = Map.of("model", model, "messages", messages, "stream", false);

    return webClient
        .post()
        .uri("/api/chat")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(payload)
        .retrieve()
        .bodyToMono(OllamaCompletion.class);
  }
}
