package com.wundermanthompson.markuply.examples.contentastemplate;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableAutoConfiguration
@AutoConfigureWebTestClient
public class PebbleTemplateProcessingTest {

  @Autowired
  private WebTestClient webClient;

  @Test
  public void pebble() throws IOException {
    testProcessingResult("pebble.html");
  }

  @Test
  public void pebble_conditional() throws IOException {
    testProcessingResult("pebble-conditional.html");
  }

  private void testProcessingResult(String inputName) throws IOException {
    String content = webClient.get().uri("/templating/" + inputName)
        .accept(MediaType.TEXT_HTML)
        .exchange()
        .expectStatus().isOk()
        .expectBody(String.class)
        .returnResult()
        .getResponseBody();

    String expected = FileUtils.readFileToString(ResourceUtils.getFile("classpath:result/" + inputName), StandardCharsets.UTF_8);

    assertThat(content).isEqualTo(expected);
  }

}
