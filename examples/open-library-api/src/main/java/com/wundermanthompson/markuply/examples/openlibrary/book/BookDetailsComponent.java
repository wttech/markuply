package com.wundermanthompson.markuply.examples.openlibrary.book;

import com.wundermanthompson.markuply.engine.component.Markuply;
import com.wundermanthompson.markuply.engine.component.method.resolver.context.Context;
import com.wundermanthompson.markuply.examples.openlibrary.book.service.BookServiceClient;
import com.wundermanthompson.markuply.javascript.JavascriptRenderer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BookDetailsComponent {

  private static final String JS_VIEW_NAME = "bookDetails";

  private final JavascriptRenderer renderer;
  private final BookServiceClient bookServiceClient;

  @Markuply("bookDetails")
  public Mono<String> render(@Context BookPageContext context) {
    String isbn = context.getIsbn();
    return bookServiceClient.getBook(isbn)
        .flatMap(book -> renderer.render(JS_VIEW_NAME, book));
  }
}
