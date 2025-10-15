package com.narlock.ollama.fetchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.narlock.ollama.model.MessageInput;
import com.narlock.ollama.model.OllamaCompletion;
import com.narlock.ollama.model.OllamaModel;
import com.narlock.ollama.service.OllamaService;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Mono;

@SpringBootTest(classes = {OllamaFetcher.class, DgsAutoConfiguration.class})
@Import(OllamaFetcher.class)
public class OllamaFetcherTest {

  @MockitoBean private OllamaService ollamaService;

  @Autowired private DgsQueryExecutor dgsQueryExecutor;

  private List<OllamaModel> mockModels;
  private OllamaCompletion mockCompletion;

  @BeforeEach
  void setup() {
    mockModels =
        List.of(
            OllamaModel.builder()
                .name("llama3")
                .modifiedAt("2025-10-14")
                .details(
                    OllamaModel.OllamaModelDetails.builder()
                        .family("llama")
                        .parameterSize("1024")
                        .quantizationLevel("Q4")
                        .build())
                .build());

    mockCompletion =
        OllamaCompletion.builder()
            .model("llama3")
            .createdAt("2025-10-13T21:00:00Z")
            .done(true)
            .message(
                OllamaCompletion.OllamaCompletionMessage.builder()
                    .role("assistant")
                    .content("Hello from the mock model!")
                    .build())
            .build();

    when(ollamaService.listModels()).thenReturn(Mono.just(mockModels));
    when(ollamaService.chatRequest("llama3", List.of(new MessageInput("user", "Hello"))))
        .thenReturn(Mono.just(mockCompletion));
  }

  @Test
  void listModels_returnsExpectedModels() {
    String query =
        """
        {
          listModels {
            name
          }
        }
        """;

    List<String> modelNames =
        dgsQueryExecutor.executeAndExtractJsonPath(query, "data.listModels[*].name");

    assertThat(modelNames).containsExactly("llama3");
    Mockito.verify(ollamaService).listModels();
  }
}
