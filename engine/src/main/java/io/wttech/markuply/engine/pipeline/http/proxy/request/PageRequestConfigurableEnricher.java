package io.wttech.markuply.engine.pipeline.http.proxy.request;

import io.wttech.markuply.engine.pipeline.http.proxy.configuration.HeaderProxyConfiguration;
import io.wttech.markuply.engine.pipeline.http.proxy.configuration.StaticHeaderConfiguration;
import lombok.Builder;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.util.context.ContextView;

import static org.springframework.web.filter.reactive.ServerWebExchangeContextFilter.EXCHANGE_CONTEXT_ATTRIBUTE;

/**
 * Retrieves {@link ServerWebExchange} from reactive context and copies headers from the original request to the external server request.
 */
@Builder
public class PageRequestConfigurableEnricher implements ReactiveRequestEnricher {

  @lombok.Builder.Default
  private final HeaderProxyConfiguration headerProxyConfiguration = HeaderProxyConfiguration.EMPTY;
  @lombok.Builder.Default
  private final StaticHeaderConfiguration staticHeaderConfiguration = StaticHeaderConfiguration.EMPTY;

  @Override
  public Mono<HttpPageRequest.Builder> enrich(HttpPageRequest.Builder builder) {
    return Mono.deferContextual(Mono::just).map(this::extractFromRequest);
  }

  private HttpPageRequest.Builder extractFromRequest(ContextView monoContext) {
    ServerHttpRequest request = ((ServerWebExchange) monoContext.get(EXCHANGE_CONTEXT_ATTRIBUTE)).getRequest();
    HttpPageRequest.Builder builder = HttpPageRequest.builder();
    request.getHeaders().entrySet().stream()
        .filter(entry -> headerProxyConfiguration.isAllowed(entry.getKey()))
        .forEach(entry -> builder.addHeader(entry.getKey(), entry.getValue()));
    staticHeaderConfiguration.getStaticHeaders().entrySet()
        .forEach(entry -> builder.addHeader(entry.getKey(), entry.getValue()));
    return builder;
  }

}
