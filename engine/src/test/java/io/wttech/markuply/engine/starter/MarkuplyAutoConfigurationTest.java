package io.wttech.markuply.engine.starter;

import io.wttech.markuply.engine.MarkuplyAutoConfiguration;
import io.wttech.markuply.engine.pipeline.http.processor.BaseHttpPageProcessor;
import io.wttech.markuply.engine.pipeline.http.repository.BaseHttpPageRepository;
import io.wttech.markuply.engine.renderer.missing.MissingComponentHandler;
import io.wttech.markuply.engine.template.parser.jsoup.JsoupTemplateParser;
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
