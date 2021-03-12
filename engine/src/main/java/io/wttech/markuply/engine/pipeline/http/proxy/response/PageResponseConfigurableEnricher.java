package io.wttech.markuply.engine.pipeline.http.proxy.response;

import io.wttech.markuply.engine.pipeline.http.proxy.configuration.HeaderProxyConfiguration;
import io.wttech.markuply.engine.pipeline.http.proxy.configuration.StaticHeaderConfiguration;
import io.wttech.markuply.engine.pipeline.http.repository.HttpPageResponse;
import lombok.Builder;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

/**
 * Retrieves {@link ServerWebExchange} from reactive context and copies headers from the external server response to the original client response.
 */
@Builder
public class PageResponseConfigurableEnricher implements PageResponseEnricher {

  @lombok.Builder.Default
  private final HeaderProxyConfiguration headerProxyConfiguration = HeaderProxyConfiguration.EMPTY;
  @lombok.Builder.Default
  private final StaticHeaderConfiguration staticHeaderConfiguration = StaticHeaderConfiguration.EMPTY;

  @Override
  public void enrich(HttpPageResponse pageResponseDetails, ServerHttpResponse httpResponse) {
    httpResponse.setRawStatusCode(pageResponseDetails.getStatusCode());
    pageResponseDetails.getHeaders().entrySet().stream()
        .filter(entry -> headerProxyConfiguration.isAllowed(entry.getKey()))
        .forEach(entry -> httpResponse.getHeaders().addAll(entry.getKey(), entry.getValue()));
    staticHeaderConfiguration.getStaticHeaders().entrySet()
        .forEach(entry -> httpResponse.getHeaders().addAll(entry.getKey(), entry.getValue()));
  }

}
