package com.wundermanthompson.markuply.engine.starter;

import com.wundermanthompson.markuply.engine.pipeline.http.processor.BaseHttpPageProcessor;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.BaseHttpPageRepository;
import com.wundermanthompson.markuply.engine.renderer.missing.MissingComponentHandler;
import com.wundermanthompson.markuply.engine.template.parser.jsoup.JsoupTemplateParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class CustomConfiguration {

  @Bean
  MissingComponentHandler provideDefaultHandler() {
    return mock(MissingComponentHandler.class);
  }

  @Bean
  JsoupTemplateParser provideDefaultTemplateParser() {
    return mock(JsoupTemplateParser.class);
  }

  @Bean
  BaseHttpPageRepository provideDefaultRepository() {
    return mock(BaseHttpPageRepository.class);
  }

  @Bean
  BaseHttpPageProcessor provideDefaultPageProcessor() {
    return mock(BaseHttpPageProcessor.class);
  }

}
