package com.narlock.ollama.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.narlock.ollama.model.MessageInput;
import com.narlock.ollama.model.OllamaCompletion;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class OllamaServiceTest {

  @Autowired private OllamaService ollamaService;

  @MockitoBean private WebClient webClient;

  @Test
  void testListModelsWithMockServer() {
    // Setup
    String body = "{\"models\":[{\"name\":\"test-model\"}]}";

    // Mock WebClient
    WebClient webClient =
        WebClient.builder()
            .exchangeFunction(
                clientRequest ->
                    Mono.just(
                        ClientResponse.create(HttpStatus.OK)
                            .header("Content-Type", "application/json")
                            .body(body)
                            .build()))
            .build();

    // Setup service
    OllamaService service = new OllamaService(webClient);

    // Verify
    StepVerifier.create(service.listModels())
        .expectNextMatches(
            models -> models.size() == 1 && "test-model".equals(models.get(0).getName()))
        .verifyComplete();
  }

  @Test
  void testChatRequestWithMockServer() throws JsonProcessingException {
    // Setup
    ObjectMapper objectMapper = new ObjectMapper();
    OllamaCompletion ollamaCompletion =
        OllamaCompletion.builder()
            .model("test-model")
            .createdAt("2025-05-10T12:00:00Z")
            .done(true)
            .message(
                OllamaCompletion.OllamaCompletionMessage.builder()
                    .role("assistant")
                    .content("Your response content goes here.")
                    .build())
            .build();
    String body = objectMapper.writeValueAsString(ollamaCompletion);

    // Mock WebClient
    WebClient webClient =
        WebClient.builder()
            .exchangeFunction(
                clientRequest ->
                    Mono.just(
                        ClientResponse.create(HttpStatusCode.valueOf(200))
                            .header("Content-Type", "application/json")
                            .body(body)
                            .build()))
            .build();

    OllamaService service = new OllamaService(webClient);

    // Request messages
    List<MessageInput> messages = List.of(new MessageInput("user", "Hello"));

    // Verify
    StepVerifier.create(service.chatRequest("test-model", messages))
        .expectNextMatches(oc -> "test-model".equals(oc.getModel()))
        .verifyComplete();
  }
}
