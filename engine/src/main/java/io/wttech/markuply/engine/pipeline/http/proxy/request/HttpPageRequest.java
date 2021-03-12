package io.wttech.markuply.engine.pipeline.http.proxy.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents data to be set on the request to the external server.
 */
@RequiredArgsConstructor
@Getter
public class HttpPageRequest {

  private static final HttpPageRequest EMPTY = HttpPageRequest.builder().build();

  private final Map<String, List<String>> headers;

  public static Builder builder() {
    return new Builder();
  }

  public static HttpPageRequest empty() {
    return EMPTY;
  }

  public static class Builder {

    private final Map<String, List<String>> headers = new HashMap<>();

    public Builder() {
    }

    /**
     * All previously set values are removed and the new header value is set as the only one.
     *
     * @param name  header name
     * @param value header value
     * @return builder
     */
    public Builder setHeader(String name, String value) {
      List<String> valueList = new ArrayList<>();
      valueList.add(value);
      headers.put(name, valueList);
      return this;
    }

    /**
     * All previously set values are removed and the new header value list is set.
     *
     * @param name   header name
     * @param values header values
     * @return builder
     */
    public Builder setHeader(String name, List<String> values) {
      List<String> valueList = new ArrayList<>(values);
      headers.put(name, valueList);
      return this;
    }

    /**
     * New header value is appended to existing ones.
     *
     * @param name  header name
     * @param value header value
     * @return builder
     */
    public Builder addHeader(String name, String value) {
      headers.computeIfAbsent(name, key -> new ArrayList<>()).add(value);
      return this;
    }

    /**
     * New header values are appended to existing ones.
     *
     * @param name   header name
     * @param values header values
     * @return builder
     */
    public Builder addHeader(String name, List<String> values) {
      values.forEach(value -> addHeader(name, value));
      return this;
    }

    /**
     * Header is removed.
     *
     * @param name header name
     * @return builder
     */
    public Builder removeHeader(String name) {
      headers.remove(name);
      return this;
    }

    public HttpPageRequest build() {
      return new HttpPageRequest(this.headers);
    }

  }

}
