package com.wundermanthompson.markuply.javascript;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import reactor.test.StepVerifier;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(TestConfiguration.class)
@EnableAutoConfiguration
@EnableConfigurationProperties
public class RendererTest {

  @Inject
  private JavascriptRenderer renderer;

  @Test
  void render() {
    StepVerifier.create(renderer.render("hello", "World"))
        .assertNext(value -> assertThat(value).isEqualTo("<div>World</div>"))
        .verifyComplete();
  }

}
