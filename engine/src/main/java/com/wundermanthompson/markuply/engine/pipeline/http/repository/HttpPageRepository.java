package com.wundermanthompson.markuply.engine.pipeline.http.repository;

import com.wundermanthompson.markuply.engine.pipeline.http.proxy.request.ReactiveRequestEnricher;
import reactor.core.publisher.Mono;

/**
 * Retrieves HTML content from external server together with response cookies, headers and other details.
 */
public interface HttpPageRepository {

  Mono<HttpPageResponse> getPage(String url);

  /**
   * Retrieves the content from external server. HTTP request is modified according to the provided request mutator.
   *
   * @param url            full URL of the resource to retrieve
   * @param requestMutator request modifications
   * @return original page content with headers
   */
  Mono<HttpPageResponse> getPage(String url, ReactiveRequestEnricher requestMutator);

}
