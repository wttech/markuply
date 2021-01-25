package com.wundermanthompson.markuply.engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.wundermanthompson.markuply.engine.configuration.ConditionalOnConfigurationProperties;
import com.wundermanthompson.markuply.engine.pipeline.classpath.ClasspathPageProcessor;
import com.wundermanthompson.markuply.engine.pipeline.classpath.ClasspathPageRepository;
import com.wundermanthompson.markuply.engine.pipeline.classpath.ClasspathPipeline;
import com.wundermanthompson.markuply.engine.pipeline.http.HttpPipeline;
import com.wundermanthompson.markuply.engine.pipeline.http.processor.BaseHttpPageProcessor;
import com.wundermanthompson.markuply.engine.pipeline.http.processor.SpringProxyHttpPageProcessor;
import com.wundermanthompson.markuply.engine.pipeline.http.proxy.request.ReactiveRequestEnricher;
import com.wundermanthompson.markuply.engine.pipeline.http.proxy.request.configuration.PageRequestConfigurableEnricherFactory;
import com.wundermanthompson.markuply.engine.pipeline.http.proxy.request.configuration.RequestEnricherSpringProperties;
import com.wundermanthompson.markuply.engine.pipeline.http.proxy.response.PageResponseConfigurableEnricherFactory;
import com.wundermanthompson.markuply.engine.pipeline.http.proxy.response.PageResponseEnricher;
import com.wundermanthompson.markuply.engine.pipeline.http.proxy.response.configuration.ResponseEnricherSpringProperties;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.BaseHttpPageRepository;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.EnrichedHttpPageRepository;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.HttpPageRepository;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.configuration.HttpRepositorySpringProperties;
import com.wundermanthompson.markuply.engine.renderer.*;
import com.wundermanthompson.markuply.engine.renderer.cache.CachedRenderFunctionProvider;
import com.wundermanthompson.markuply.engine.renderer.cache.RenderFunctionCache;
import com.wundermanthompson.markuply.engine.renderer.cache.RenderFunctionCacheProperties;
import com.wundermanthompson.markuply.engine.renderer.error.ComponentErrorHandler;
import com.wundermanthompson.markuply.engine.renderer.error.ComponentErrorHandlers;
import com.wundermanthompson.markuply.engine.renderer.missing.HtmlCommentHandler;
import com.wundermanthompson.markuply.engine.renderer.missing.MissingComponentHandler;
import com.wundermanthompson.markuply.engine.renderer.registry.ComponentRegistry;
import com.wundermanthompson.markuply.engine.template.parser.TemplateParser;
import com.wundermanthompson.markuply.engine.template.parser.atto.AttoTemplateParser;
import com.wundermanthompson.markuply.engine.webclient.WebClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.reactive.ServerWebExchangeContextFilter;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Configuration
@Import({MarkuplyBaseConfiguration.class})
@Slf4j
public class MarkuplyAutoConfiguration {

  @Bean
  ServerWebExchangeContextFilter serverWebExchangeContextFilter() {
    return new ServerWebExchangeContextFilter();
  }

  @Bean
  ComponentRegistry provideRegistry() {
    return ComponentRegistry.instance();
  }

  @Bean
  @ConditionalOnMissingBean
  ComponentErrorHandler provideDefaultErrorHandler() {
    return ComponentErrorHandlers.logAll();
  }

  @Bean
  @ConditionalOnMissingBean
  MissingComponentHandler provideDefaultHandler() {
    return HtmlCommentHandler.instance();
  }

  @Bean
  @ConditionalOnMissingBean
  TemplateParser provideDefaultTemplateParser() {
    return AttoTemplateParser.instance();
  }

  @Bean
  @ConditionalOnMissingBean
  ObjectMapper provideDefaultMapper() {
    return new ObjectMapper();
  }

  @Bean
  @ConditionalOnMissingBean
  WebClient provideDefaultWebClient() {
    return WebClientBuilder.instance()
        .readTimeoutMillis(15 * 1000)
        .connectTimeoutMillis(15 * 1000)
        .maxSize(5 * 1024 * 2014)
        .enableGzip()
        .build();
  }

  @Bean
  @ConditionalOnMissingBean
  RenderFunctionFactory provideDefaultFactory(TemplateParser templateParser, ComponentRenderer componentRenderer) {
    return DefaultRenderFunctionFactory.instance(templateParser, componentRenderer);
  }

  @Bean
  @ConditionalOnConfigurationProperties(prefix = RenderFunctionCacheProperties.PREFIX, targetClass = RenderFunctionCacheProperties.class)
  @ConditionalOnMissingBean
  RenderFunctionCache cacheFromProperties(RenderFunctionCacheProperties properties, RenderFunctionFactory factory) {
    Caffeine<Object, Object> cache = Caffeine.newBuilder()
        .maximumSize(properties.getMaxSize())
        .expireAfterAccess(properties.getExpireAfterAccess());
    return RenderFunctionCache.instance(factory, cache);
  }

  @Bean
  @ConditionalOnMissingBean
  RenderFunctionProvider defaultRenderFunctionProvider(RenderFunctionFactory factory, Optional<RenderFunctionCache> cache) {
    if (cache.isPresent()) {
      return CachedRenderFunctionProvider.instance(cache.get());
    } else {
      return DirectRenderFunctionProvider.instance(factory);
    }
  }

  @Bean
  @ConditionalOnConfigurationProperties(prefix = RequestEnricherSpringProperties.PREFIX, targetClass = RequestEnricherSpringProperties.class)
  @ConditionalOnMissingBean
  ReactiveRequestEnricher providesDefaultEnricherIfPropertiesExist(RequestEnricherSpringProperties properties, PageRequestConfigurableEnricherFactory factory) {
    return factory.fromProperties(properties);
  }

  @Bean
  @ConditionalOnConfigurationProperties(prefix = ResponseEnricherSpringProperties.PREFIX, targetClass = ResponseEnricherSpringProperties.class)
  @ConditionalOnMissingBean
  PageResponseEnricher providesDefaultResponseEnricherIfPropertiesExist(ResponseEnricherSpringProperties properties, PageResponseConfigurableEnricherFactory factory) {
    return factory.fromProperties(properties);
  }

  @Bean
  @ConditionalOnConfigurationProperties(prefix = HttpRepositorySpringProperties.PREFIX, targetClass = HttpRepositorySpringProperties.class)
  @ConditionalOnMissingBean({HttpPageRepository.class})
  HttpPageRepository webPageRepository(WebClient webClient, HttpRepositorySpringProperties properties, Optional<ReactiveRequestEnricher> pageRequestEnricherOptional) {
    BaseHttpPageRepository baseRepository = BaseHttpPageRepository.of(webClient, properties.getUrlPrefix());
    if (pageRequestEnricherOptional.isPresent()) {
      return EnrichedHttpPageRepository.instance(baseRepository, pageRequestEnricherOptional.get());
    } else {
      return baseRepository;
    }
  }

  @Bean
  @ConditionalOnBean({HttpPageRepository.class})
  @ConditionalOnMissingBean(HttpPipeline.class)
  HttpPipeline webPageProcessor(HttpPageRepository pageRepository, RenderFunctionProvider renderFunctionProvider, Optional<PageResponseEnricher> enricher) {
    BaseHttpPageProcessor baseProcessor = BaseHttpPageProcessor.of(pageRepository, renderFunctionProvider);
    if (enricher.isPresent()) {
      return SpringProxyHttpPageProcessor.instance(baseProcessor, enricher.get());
    } else {
      return baseProcessor;
    }
  }

  @Bean
  @ConditionalOnMissingBean(ClasspathPageRepository.class)
  ClasspathPageRepository provideClasspathPageRepository() {
    return ClasspathPageRepository.instance();
  }

  @Bean
  @ConditionalOnBean(ClasspathPageRepository.class)
  @ConditionalOnMissingBean
  ClasspathPipeline provideClasspathPageProcessor(ClasspathPageRepository pageRepository, RenderFunctionProvider renderFunctionProvider) {
    return ClasspathPageProcessor.of(pageRepository, renderFunctionProvider);
  }

}
