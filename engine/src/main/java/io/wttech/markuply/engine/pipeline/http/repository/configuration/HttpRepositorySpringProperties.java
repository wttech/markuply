package io.wttech.markuply.engine.pipeline.http.repository.configuration;

import io.wttech.markuply.engine.configuration.OptionalProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * Represents properties used to configure HTTP page repository.
 */
@ConfigurationProperties(HttpRepositorySpringProperties.PREFIX)
@ConstructorBinding
@RequiredArgsConstructor
@Getter
public class HttpRepositorySpringProperties implements OptionalProperties {

  public static final String PREFIX = "markuply.http.repository";

  private final String urlPrefix;

  @Override
  public boolean isPresent() {
    return urlPrefix != null;
  }

}
