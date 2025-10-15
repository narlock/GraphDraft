package com.narlock.ollama.fetchers;

import com.narlock.ollama.model.MessageInput;
import com.narlock.ollama.model.OllamaCompletion;
import com.narlock.ollama.model.OllamaModel;
import com.narlock.ollama.service.OllamaService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import java.util.List;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@DgsComponent
@RequiredArgsConstructor
public class OllamaFetcher {
  private final OllamaService ollamaService;

  @DgsQuery
  public Mono<List<OllamaModel>> listModels() {
    return ollamaService.listModels();
  }

  @DgsQuery
  public Mono<OllamaCompletion> chatRequest(
      @InputArgument String model, @InputArgument List<MessageInput> messages) {
    return ollamaService.chatRequest(model, messages);
  }
}
