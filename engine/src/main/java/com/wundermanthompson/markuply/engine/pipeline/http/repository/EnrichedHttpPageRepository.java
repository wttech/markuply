package com.wundermanthompson.markuply.engine.pipeline.http.repository;

import com.wundermanthompson.markuply.engine.pipeline.http.proxy.request.ReactiveRequestEnricher;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Retrieves HTML content from an external server using the original {@link HttpPageRepository}.
 * Enriches the request with data from the original request.
 */
@RequiredArgsConstructor(staticName = "instance")
public class EnrichedHttpPageRepository implements HttpPageRepository {

  @NonNull
  private final HttpPageRepository httpPageRepository;
  @NonNull
  private final ReactiveRequestEnricher enricher;

  @Override
  public Mono<HttpPageResponse> getPage(String url) {
    return httpPageRepository.getPage(url, this.enricher::enrich);
  }

  @Override
  public Mono<HttpPageResponse> getPage(String url, ReactiveRequestEnricher requestMutator) {
    ReactiveRequestEnricher combined = builder -> this.enricher.enrich(builder).flatMap(requestMutator::enrich);
    return httpPageRepository.getPage(url, combined);
  }
}
