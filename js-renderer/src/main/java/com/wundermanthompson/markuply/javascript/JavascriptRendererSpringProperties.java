package com.wundermanthompson.markuply.javascript;

import com.wundermanthompson.markuply.engine.configuration.OptionalProperties;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * Represents properties defining the Javascript renderer.
 */
@ConfigurationProperties(prefix = JavascriptRendererSpringProperties.PREFIX)
@ConstructorBinding
@Getter
public class JavascriptRendererSpringProperties implements OptionalProperties {

  public static final String PREFIX = "markuply.javascript";

  private final String bundle;
  private final boolean production;

  public JavascriptRendererSpringProperties(String bundle, boolean production) {
    this.bundle = bundle;
    this.production = production;
  }

  @Override
  public boolean isPresent() {
    return bundle != null;
  }

}
