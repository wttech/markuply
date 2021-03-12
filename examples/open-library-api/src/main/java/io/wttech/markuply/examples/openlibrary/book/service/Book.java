package io.wttech.markuply.examples.openlibrary.book.service;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class Book {

  private List<Publisher> publishers;
  private Map<String, List<String>> identifiers;
  private String weight;
  private String title;
  private String url;
  @JsonAlias({"number_of_pages"})
  private int numberOfPages;
  private Map<String, String> cover;
  @JsonAlias({"publish_date"})
  private String publishDate;
  private List<Author> authors;

}
