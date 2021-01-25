package com.wundermanthompson.markuply.engine.pipeline.http.repository.configuration;

import com.wundermanthompson.markuply.engine.pipeline.http.proxy.request.ReactiveRequestEnricher;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.BaseHttpPageRepository;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.EnrichedHttpPageRepository;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.HttpPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.inject.Provider;

/**
 * DSL for constructing {@link HttpPageRepository} with default dependencies provided by Spring.
 */
@RequiredArgsConstructor
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HttpPageRepositorySpringConfigurator {

  private static final WebClient DEFAULT_WEB_CLIENT = WebClient.builder().build();

  private final Provider<WebClient> webClientProvider;
  private final Provider<ReactiveRequestEnricher> enricherProvider;

  private WebClient webClient;
  private ReactiveRequestEnricher enricher;
  private String uriPrefix;

  public HttpPageRepositorySpringConfigurator urlPrefix(String uriPrefix) {
    this.uriPrefix = uriPrefix;
    return this;
  }

  public HttpPageRepositorySpringConfigurator webClient(WebClient webClient) {
    this.webClient = webClient;
    return this;
  }

  public HttpPageRepositorySpringConfigurator enricher(ReactiveRequestEnricher enricher) {
    this.enricher = enricher;
    return this;
  }

  public HttpPageRepository build() {
    WebClient finalWebClient = calculateFinalWebClient();
    ReactiveRequestEnricher finalEnricher = calculateFinalEnricher();
    BaseHttpPageRepository baseRepository = BaseHttpPageRepository.of(finalWebClient, uriPrefix);
    if (finalEnricher != null) {
      return EnrichedHttpPageRepository.instance(baseRepository, finalEnricher);
    } else {
      return baseRepository;
    }
  }

  private ReactiveRequestEnricher calculateFinalEnricher() {
    return calculateFinalValue(this.enricher, this.enricherProvider, null);
  }

  private WebClient calculateFinalWebClient() {
    return calculateFinalValue(this.webClient, this.webClientProvider, DEFAULT_WEB_CLIENT);
  }

  private <T> T calculateFinalValue(T direct, Provider<T> provider, T defaultValue) {
    // use Optional.or when moving to Java 9+
    if (direct == null) {
      T fromProvider = provider.get();
      if (fromProvider == null) {
        return defaultValue;
      } else {
        return fromProvider;
      }
    } else {
      return direct;
    }
  }

}
