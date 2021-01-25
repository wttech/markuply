package com.wundermanthompson.markuply.engine.pipeline.http.proxy.configuration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents configuration for adding static headers.
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class StaticHeaderConfiguration {

  public static final StaticHeaderConfiguration EMPTY = StaticHeaderConfiguration.builder().build();

  private final Map<String, List<String>> staticHeaders;

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private final Map<String, List<String>> staticHeaders = new HashMap<>();

    public Builder setHeader(String name, List<String> values) {
      staticHeaders.put(name, values);
      return this;
    }

    public Builder setHeader(String name, String value) {
      staticHeaders.put(name, Collections.singletonList(value));
      return this;
    }

    public StaticHeaderConfiguration build() {
      return new StaticHeaderConfiguration(staticHeaders);
    }

  }

}
