package io.wttech.markuply.engine.pipeline.http.proxy.response;

import io.wttech.markuply.engine.pipeline.http.repository.HttpPageResponse;
import org.springframework.http.server.reactive.ServerHttpResponse;

/**
 * Maps template response data to the original client response.
 */
public interface PageResponseEnricher {

  void enrich(HttpPageResponse pageResponseDetails, ServerHttpResponse httpResponse);

}
