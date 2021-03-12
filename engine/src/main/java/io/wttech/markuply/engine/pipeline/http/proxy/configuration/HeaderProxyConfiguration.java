package io.wttech.markuply.engine.pipeline.http.proxy.configuration;

import io.wttech.markuply.engine.pipeline.http.proxy.rule.HeaderMatchingRule;
import io.wttech.markuply.engine.pipeline.http.proxy.rule.HeaderMatchingRuleFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents configuration for copying headers.
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class HeaderProxyConfiguration {

  public static final HeaderProxyConfiguration EMPTY = HeaderProxyConfiguration.builder().build();

  private final List<HeaderMatchingRule> rules;

  public static Builder builder() {
    return new Builder();
  }

  public boolean isAllowed(String name) {
    return rules.stream()
        .anyMatch(rule -> rule.matches(name));
  }

  public static class Builder {

    private static final HeaderMatchingRuleFactory factory = HeaderMatchingRuleFactory.instance();
    private final List<HeaderMatchingRule> rules = new ArrayList<>();

    public Builder allow(String pattern) {
      rules.add(factory.allow(pattern));
      return this;
    }

    public Builder pattern(String pattern) {
      rules.add(factory.create(pattern));
      return this;
    }

    public HeaderProxyConfiguration build() {
      return new HeaderProxyConfiguration(this.rules);
    }

  }

}
