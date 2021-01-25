package com.wundermanthompson.markuply.engine.renderer.cache;

import com.wundermanthompson.markuply.engine.configuration.OptionalProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.Duration;

@ConfigurationProperties(RenderFunctionCacheProperties.PREFIX)
@ConstructorBinding
@RequiredArgsConstructor
public class RenderFunctionCacheProperties implements OptionalProperties {

  public static final String PREFIX = "markuply.cache.render";

  public static final Duration DEFAULT_EXPIRY = Duration.ofSeconds(1);
  public static final long DEFAULT_MAX_SIZE = 1000;

  private final boolean enabled;
  private final Duration expireAfterAccess;
  private final Long maxSize;

  @Override
  public boolean isPresent() {
    return enabled;
  }

  public Duration getExpireAfterAccess() {
    return expireAfterAccess != null ? expireAfterAccess : DEFAULT_EXPIRY;
  }

  public Long getMaxSize() {
    return maxSize != null ? maxSize : DEFAULT_MAX_SIZE;
  }
}
