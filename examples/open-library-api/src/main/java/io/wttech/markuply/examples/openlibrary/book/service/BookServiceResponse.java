package io.wttech.markuply.examples.openlibrary.book.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Getter
public class BookServiceResponse {

  private Map<String, Book> books;

  public Optional<Book> getSingleBook() {
    return books.values().stream().findFirst();
  }

}
