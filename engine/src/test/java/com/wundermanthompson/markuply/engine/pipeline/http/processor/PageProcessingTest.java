package com.wundermanthompson.markuply.engine.pipeline.http.processor;

import com.wundermanthompson.markuply.engine.pipeline.classpath.ClasspathPipeline;
import com.wundermanthompson.markuply.engine.pipeline.context.PageContext;
import com.wundermanthompson.markuply.engine.utils.ClasspathFileLoader;
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
public class PageProcessingTest {

  private final ClasspathFileLoader loader = ClasspathFileLoader.instance();

  @Inject
  private ClasspathPipeline pageProcessor;

  @Test
  void testPageRender() {
    StepVerifier.create(pageProcessor.render("/pages/test.html", PageContext.empty()))
        .assertNext(content ->
            assertThat(content)
                .isEqualTo(loader.loadBlocking("/result/test.html")))
        .verifyComplete();
  }

  @Test
  void testComponentError() {
    StepVerifier.create(pageProcessor.render("/pages/error.html", PageContext.empty()))
        .assertNext(content ->
            assertThat(content)
                .isEqualTo(loader.loadBlocking("/result/error.html")))
        .verifyComplete();
  }

  @Test
  void testNamedSectionRender() {
    StepVerifier.create(pageProcessor.render("/pages/renderNamedSection.html", PageContext.empty()))
        .assertNext(content ->
            assertThat(content)
                .isEqualTo(loader.loadBlocking("/result/renderNamedSection.html")))
        .verifyComplete();
  }

  @Test
  void testDefaultSectionRender() {
    StepVerifier.create(pageProcessor.render("/pages/renderDefaultSectionNextToNamedOne.html", PageContext.empty()))
        .assertNext(content ->
            assertThat(content)
                .isEqualTo(loader.loadBlocking("/result/renderDefaultSectionNextToNamedOne.html")))
        .verifyComplete();
  }

  @Test
  void testImplicitDefaultSectionRender() {
    StepVerifier.create(pageProcessor.render("/pages/renderImplicitDefaultSection.html", PageContext.empty()))
        .assertNext(content ->
            assertThat(content)
                .isEqualTo(loader.loadBlocking("/result/renderImplicitDefaultSection.html")))
        .verifyComplete();
  }

  @Test
  void testComponentInHead() {
    StepVerifier.create(pageProcessor.render("/pages/componentInHead.html", PageContext.empty()))
        .assertNext(
            content ->
                assertThat(content)
                    .isEqualTo(loader.loadBlocking("/result/componentInHead.html")))
        .verifyComplete();
  }

  @Test
  void testAdjacentComponents() {
    StepVerifier.create(
        pageProcessor.render("/pages/twoAdjacentComponents.html", PageContext.empty()))
        .assertNext(
            value ->
                assertThat(value)
                    .isEqualTo(loader.loadBlocking("/result/twoAdjacentComponents.html")))
        .verifyComplete();
  }

}
