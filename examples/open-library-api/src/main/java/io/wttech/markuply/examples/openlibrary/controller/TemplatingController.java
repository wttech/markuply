package io.wttech.markuply.examples.openlibrary.controller;

import io.wttech.markuply.examples.openlibrary.book.BookPageProcessor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class TemplatingController {

  private final BookPageProcessor bookPageProcessor;

  @GetMapping(value = "/books/{isbn}", produces = MediaType.TEXT_HTML_VALUE)
  @Operation(summary = "Renders the book details page")
  @ApiResponse(responseCode = "200", description = "Book details page")
  @ApiResponse(responseCode = "404", description = "Book not found")
  public Mono<String> bookPage(@PathVariable String isbn) {
    return bookPageProcessor.render(isbn);
  }

}
