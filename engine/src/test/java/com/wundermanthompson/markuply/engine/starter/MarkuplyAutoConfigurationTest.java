package com.wundermanthompson.markuply.engine.starter;

import com.wundermanthompson.markuply.engine.MarkuplyAutoConfiguration;
import com.wundermanthompson.markuply.engine.pipeline.http.processor.BaseHttpPageProcessor;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.BaseHttpPageRepository;
import com.wundermanthompson.markuply.engine.renderer.missing.MissingComponentHandler;
import com.wundermanthompson.markuply.engine.template.parser.jsoup.JsoupTemplateParser;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

public class MarkuplyAutoConfigurationTest {

  private final ReactiveWebApplicationContextRunner contextRunner = new ReactiveWebApplicationContextRunner()
      .withConfiguration(AutoConfigurations.of(MarkuplyAutoConfiguration.class));

  @Test
  void checkMissingComponentHandlerOverride() {
    testBeanOfType(MissingComponentHandler.class);
  }

  @Test
  void checkTemplateParserOverride() {
    testBeanOfType(JsoupTemplateParser.class);
  }

  @Test
  void checkPageRepositoryOverride() {
    testBeanOfType(BaseHttpPageRepository.class);
  }

  @Test
  void checkPageProcessorOverride() {
    testBeanOfType(BaseHttpPageProcessor.class);
  }

  void testBeanOfType(Class<?> clazz) {
    this.contextRunner.withUserConfiguration(CustomConfiguration.class).run((context) -> {
      assertThat(context).hasSingleBean(clazz);
      assertThat(context).getBean(clazz).matches(bean -> Mockito.mockingDetails(bean).isMock());
    });
  }

}
