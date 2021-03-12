package io.wttech.markuply.engine.pipeline.http.repository;

import io.wttech.markuply.engine.pipeline.http.proxy.request.HttpPageRequest;
import io.wttech.markuply.engine.pipeline.http.proxy.request.ReactiveRequestEnricher;
import io.wttech.markuply.engine.pipeline.repository.PageRepositoryException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * Retrieves HTML content from external server using WebClient together with response cookies, headers and other details.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(access = AccessLevel.PACKAGE)
public class BaseHttpPageRepository implements HttpPageRepository {

  private static final List<ExchangeFilterFunction> LOGGING_FILTERS = Arrays.asList(logRequest(), logResponse());

  @NonNull
  private final WebClient webClient;
  @NonNull
  private final String uriPrefix;

  public static BaseHttpPageRepository of(WebClient webClient, String uriPrefix) {
    WebClient clientWithLogging = webClient.mutate().filters(filters -> filters.addAll(LOGGING_FILTERS)).build();
    return new BaseHttpPageRepository(clientWithLogging, uriPrefix);
  }

  @Override
  public Mono<HttpPageResponse> getPage(String url) {
    return sendRequest(url, HttpPageRequest.empty());
  }

  @Override
  public Mono<HttpPageResponse> getPage(String url, ReactiveRequestEnricher requestMutator) {
    return requestMutator.enrich(HttpPageRequest.builder())
        .flatMap(builder -> sendRequest(url, builder.build()));
  }

  private Mono<HttpPageResponse> sendRequest(String url, HttpPageRequest requestDetails) {
    WebClient.RequestHeadersUriSpec<?> requestBuilder = webClient.get();
    requestBuilder.headers(httpHeaders ->
        requestDetails.getHeaders().forEach(httpHeaders::addAll)
    );
    try {
      URI uri = new URI(uriPrefix + url);
      return requestBuilder.uri(uri)
          .retrieve()
          .onStatus(status -> true, response -> Mono.empty())
          .toEntity(String.class)
          .map(this::toMarkuplyResponse);
    } catch (URISyntaxException e) {
      return Mono.error(new PageRepositoryException("The original HTML resource URL is not valid", e));
    }

  }

  private HttpPageResponse toMarkuplyResponse(ResponseEntity<String> response) {
    HttpPageResponse.Builder responseBuilder = HttpPageResponse.builder(response.getStatusCodeValue());
    response.getHeaders().forEach(responseBuilder::addHeader);
    responseBuilder.body(response.getBody());
    return responseBuilder.build();
  }

  private static ExchangeFilterFunction logRequest() {
    return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
      if (log.isDebugEnabled()) {
        StringBuilder sb = new StringBuilder("Original HTML request\n");
        sb.append("URL: ").append(clientRequest.url().toString()).append("\n");
        clientRequest
            .headers()
            .forEach((name, values) -> values.forEach(value -> sb.append("Header: ").append(name).append(":").append(String.join(",", values)).append("\n")));
        log.debug(sb.toString());
      }
      return Mono.just(clientRequest);
    });
  }

  private static ExchangeFilterFunction logResponse() {
    return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
      if (log.isDebugEnabled()) {
        StringBuilder sb = new StringBuilder("Original HTML response: \n");
        sb.append("Status code: ").append(clientResponse.statusCode().name());
        clientResponse
            .headers()
            .asHttpHeaders()
            .forEach((name, values) -> values.forEach(value -> sb.append("Header: ").append(name).append(":").append(String.join(",", values)).append("\n")));
        log.debug(sb.toString());
      }
      return Mono.just(clientResponse);
    });
  }

}
