package io.wttech.markuply.engine.pipeline.http.proxy.response.configuration;

import io.wttech.markuply.engine.configuration.OptionalProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

/**
 * Represents Spring properties for the response proxy feature.
 */
@ConfigurationProperties(ResponseEnricherSpringProperties.PREFIX)
@ConstructorBinding
@RequiredArgsConstructor
@Getter
public class ResponseEnricherSpringProperties implements OptionalProperties {

  public static final String PREFIX = "markuply.http.proxy.response";

  /*
   * Treats all strings as regex patterns. They are evaluates in order. The last matching entry defines if header will be copied or not.
   * Exclamation mark at the start indicates negation.
   * Pattern is matched against the whole header name.
   */
  private final List<String> copyHeaders;

  /*
   * List of headers to be added to the requests defined in the form "headerName: headerValue1, headerValue2".
   */
  private final List<String> addHeaders;

  @Override
  public boolean isPresent() {
    return copyHeaders != null && addHeaders != null;
  }

}
