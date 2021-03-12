package io.wttech.markuply.examples.openlibrary.book;

import io.wttech.graal.templating.javascript.JavascriptRenderer;
import io.wttech.markuply.engine.component.Markuply;
import io.wttech.markuply.engine.component.method.resolver.context.Context;
import io.wttech.markuply.examples.openlibrary.book.service.BookServiceClient;
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
