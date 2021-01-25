package com.wundermanthompson.markuply.examples.openlibrary.book;

import com.wundermanthompson.markuply.engine.component.Markuply;
import com.wundermanthompson.markuply.engine.pipeline.context.PageContext;
import com.wundermanthompson.markuply.examples.openlibrary.book.service.BookServiceClient;
import com.wundermanthompson.markuply.javascript.JavascriptRenderer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BookMetadataComponent {

  private static final String JS_VIEW_NAME = "bookMetadata";

  private final JavascriptRenderer renderer;
  private final BookServiceClient bookServiceClient;

  @Markuply("bookMetadata")
  public Mono<String> render(PageContext context) {
    String isbn = context.get(BookPageContext.class).getIsbn();
    return bookServiceClient.getBook(isbn)
        .flatMap(book -> renderer.render(JS_VIEW_NAME, book));
  }

}
