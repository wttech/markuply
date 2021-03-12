package io.wttech.markuply.engine.pipeline.http.processor;

import io.wttech.markuply.engine.pipeline.context.PageContext;
import io.wttech.markuply.engine.pipeline.http.HttpPipeline;
import io.wttech.markuply.engine.pipeline.http.proxy.request.RequestEnricher;
import io.wttech.markuply.engine.pipeline.http.proxy.response.PageResponseEnricher;
import io.wttech.markuply.engine.pipeline.http.repository.HttpPageResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

import static org.springframework.web.filter.reactive.ServerWebExchangeContextFilter.EXCHANGE_CONTEXT_ATTRIBUTE;

/**
 * Overlays basic {@link HttpPipeline} with {@link PageResponseEnricher}
 * to map external server response headers onto the response to the original client.
 */
@RequiredArgsConstructor(staticName = "instance")
public class SpringProxyHttpPageProcessor implements HttpPipeline {

  @NonNull
  private final HttpPipeline processor;
  @NonNull
  private final PageResponseEnricher responseEnricher;

  @Override
  public Mono<HttpPageResponse> render(String path) {
    return withProxy(() -> processor.render(path));
  }

  @Override
  public Mono<HttpPageResponse> render(String path, RequestEnricher enricher) {
    return withProxy(() -> processor.render(path, enricher));
  }

  @Override
  public Mono<HttpPageResponse> render(String path, PageContext context) {
    return withProxy(() -> processor.render(path, context));
  }

  @Override
  public Mono<HttpPageResponse> render(String path, PageContext context, RequestEnricher enricher) {
    return withProxy(() -> processor.render(path, context, enricher));
  }

  private Mono<HttpPageResponse> withProxy(Supplier<Mono<HttpPageResponse>> supplier) {
    return Mono.deferContextual(Mono::just)
        .map(context -> ((ServerWebExchange) context.get(EXCHANGE_CONTEXT_ATTRIBUTE)).getResponse())
        .flatMap(serverResponse -> enrichResponse(serverResponse, supplier));
  }

  private Mono<HttpPageResponse> enrichResponse(ServerHttpResponse serverResponse, Supplier<Mono<HttpPageResponse>> supplier) {
    return supplier.get().doOnNext(response -> responseEnricher.enrich(response, serverResponse));
  }

}
