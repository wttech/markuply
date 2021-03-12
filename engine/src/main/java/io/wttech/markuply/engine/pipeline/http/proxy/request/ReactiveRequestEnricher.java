package io.wttech.markuply.engine.pipeline.http.proxy.request;

import reactor.core.publisher.Mono;

/**
 * Reactive version of {@link RequestEnricher}.
 */
public interface ReactiveRequestEnricher {

  Mono<HttpPageRequest.Builder> enrich(HttpPageRequest.Builder builder);

}
