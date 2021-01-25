package com.wundermanthompson.markuply.engine.pipeline.http.proxy.response;

import com.wundermanthompson.markuply.engine.pipeline.http.proxy.configuration.HeaderPropertiesParser;
import com.wundermanthompson.markuply.engine.pipeline.http.proxy.response.configuration.ResponseEnricherSpringProperties;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Factory creating {@link PageResponseConfigurableEnricher} from Spring properties.
 */
@Component
@RequiredArgsConstructor
public class PageResponseConfigurableEnricherFactory {

  @NonNull
  private final HeaderPropertiesParser parser;

  public PageResponseConfigurableEnricher fromProperties(ResponseEnricherSpringProperties properties) {
    return PageResponseConfigurableEnricher.builder()
        .headerProxyConfiguration(parser.parseCopyHeaderConfiguration(properties.getCopyHeaders()))
        .staticHeaderConfiguration(parser.parseStaticHeaderConfiguration(properties.getAddHeaders()))
        .build();
  }

}
