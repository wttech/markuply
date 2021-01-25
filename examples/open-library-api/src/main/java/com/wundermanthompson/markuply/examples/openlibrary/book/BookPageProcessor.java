package com.wundermanthompson.markuply.examples.openlibrary.book;

import com.wundermanthompson.markuply.engine.pipeline.context.PageContext;
import com.wundermanthompson.markuply.engine.pipeline.http.HttpPipeline;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.HttpPageResponse;
import com.wundermanthompson.markuply.examples.openlibrary.book.service.BookServiceClient;
import com.wundermanthompson.markuply.examples.openlibrary.controller.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BookPageProcessor {

  private final BookServiceClient bookServiceClient;
  private final HttpPipeline pageProcessor;

  public Mono<String> render(String isbn) {
    return bookServiceClient.getBook(isbn)
        .switchIfEmpty(Mono.error(new NotFoundException()))
        .flatMap(book -> pageProcessor.render("/templates/book.html", createContext(isbn))
            .map(HttpPageResponse::getBody));
  }

  private PageContext createContext(String isbn) {
    return PageContext.of(BookPageContext.of(isbn));
  }

}
