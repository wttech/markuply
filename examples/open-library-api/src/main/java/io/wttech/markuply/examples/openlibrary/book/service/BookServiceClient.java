package io.wttech.markuply.examples.openlibrary.book.service;

import io.wttech.markuply.engine.request.cache.RequestCache;
import io.wttech.markuply.examples.openlibrary.circuit.CircuitBreakers;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class BookServiceClient {

  private final CircuitBreakers circuitBreakers;

  private final WebClient webClient = WebClient.builder().build();
  private final ParameterizedTypeReference<Map<String, Book>> RESPONSE_TYPE_REF = new ParameterizedTypeReference<Map<String, Book>>() {
  };

  /**
   * See <a href="https://openlibrary.org/api/books?bibkeys=ISBN:9780980200447&amp;jscmd=data&amp;format=json">Open library example</a>
   *
   * @param isbn ID of the book to retrieve
   * @return book details
   */
  @RequestCache
  public Mono<Book> getBook(String isbn) {
    return circuitBreakers.circuitBreak(callExternal(isbn))
        .flatMap(response -> Mono.justOrEmpty(response.getSingleBook()));
  }

  public Mono<BookServiceResponse> callExternal(String isbn) {
    return webClient.get().uri(uriBuilder ->
        uriBuilder
            .scheme("http")
            .host("openlibrary.org")
            .path("/api/books")
            .queryParam("bibkeys", "ISBN:" + isbn)
            .queryParam("jscmd", "data")
            .queryParam("format", "json")
            .build())
        .retrieve()
        .bodyToMono(RESPONSE_TYPE_REF)
        .map(BookServiceResponse::new);
  }

}
