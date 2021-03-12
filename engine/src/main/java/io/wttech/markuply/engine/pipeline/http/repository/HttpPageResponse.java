package io.wttech.markuply.engine.pipeline.http.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents response received from external response.
 */
@RequiredArgsConstructor
@Getter
public class HttpPageResponse {

  private final int statusCode;
  private final Map<String, List<String>> headers;
  private final String body;

  public static HttpPageResponse.Builder builder(int statusCode) {
    return new HttpPageResponse.Builder(statusCode);
  }

  public HttpPageResponse withBody(String content) {
    return new HttpPageResponse(this.statusCode, this.headers, content);
  }

  @RequiredArgsConstructor
  public static class Builder {

    private final int statusCode;
    private String body;
    private final Map<String, List<String>> headers = new HashMap<>();

    public HttpPageResponse.Builder addHeader(String name, String value) {
      headers.computeIfAbsent(name, key -> new ArrayList<>()).add(value);
      return this;
    }

    public HttpPageResponse.Builder addHeader(String name, List<String> values) {
      values.forEach(value -> addHeader(name, value));
      return this;
    }

    public HttpPageResponse.Builder body(String content) {
      this.body = content;
      return this;
    }

    public HttpPageResponse build() {
      return new HttpPageResponse(this.statusCode, this.headers, this.body);
    }

  }

}
