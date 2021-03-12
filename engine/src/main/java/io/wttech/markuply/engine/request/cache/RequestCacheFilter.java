package io.wttech.markuply.engine.request.cache;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/**
 * For each request an instance of {@link RequestScopedCache} is registered within subscriber
 * context to be consumed upstream.
 */
public class RequestCacheFilter implements WebFilter {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    return chain.filter(exchange).contextWrite(Context.of(
        RequestScopedCache.class, RequestScopedCache.instance()));
  }

}
