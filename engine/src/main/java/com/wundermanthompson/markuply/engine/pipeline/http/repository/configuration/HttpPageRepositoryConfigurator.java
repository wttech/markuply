package com.wundermanthompson.markuply.engine.pipeline.http.repository.configuration;

import com.wundermanthompson.markuply.engine.pipeline.http.proxy.request.ReactiveRequestEnricher;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.BaseHttpPageRepository;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.EnrichedHttpPageRepository;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.HttpPageRepository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

public class HttpPageRepositoryConfigurator {

  private WebClient webClient;
  private String uriPrefix = "";
  private ReactiveRequestEnricher enricher;

  public static HttpPageRepositoryConfigurator instance() {
    return new HttpPageRepositoryConfigurator();
  }

  public HttpPageRepositoryConfigurator uriPrefix(String uriPrefix) {
    this.uriPrefix = uriPrefix;
    return this;
  }

  public HttpPageRepositoryConfigurator webClient(WebClient webClient) {
    this.webClient = webClient;
    return this;
  }

  public HttpPageRepositoryConfigurator enricher(ReactiveRequestEnricher enricher) {
    this.enricher = enricher;
    return this;
  }

  public HttpPageRepository build() {
    WebClient webClient = Optional.ofNullable(this.webClient).orElseGet(() -> WebClient.builder().build());
    BaseHttpPageRepository baseRepository = BaseHttpPageRepository.of(webClient, uriPrefix);
    if (this.enricher != null) {
      return EnrichedHttpPageRepository.instance(baseRepository, this.enricher);
    } else {
      return baseRepository;
    }
  }

}
