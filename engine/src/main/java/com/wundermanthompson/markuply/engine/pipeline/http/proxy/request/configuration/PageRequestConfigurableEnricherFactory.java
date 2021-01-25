package com.wundermanthompson.markuply.engine.pipeline.http.proxy.request.configuration;

import com.wundermanthompson.markuply.engine.pipeline.http.proxy.configuration.HeaderPropertiesParser;
import com.wundermanthompson.markuply.engine.pipeline.http.proxy.request.PageRequestConfigurableEnricher;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Factory creating {@link PageRequestConfigurableEnricher} from Spring properties.
 */
@Component
@RequiredArgsConstructor
public class PageRequestConfigurableEnricherFactory {

  @NonNull
  private final HeaderPropertiesParser parser;

  public PageRequestConfigurableEnricher fromProperties(RequestEnricherSpringProperties properties) {
    return PageRequestConfigurableEnricher.builder()
        .headerProxyConfiguration(parser.parseCopyHeaderConfiguration(properties.getCopyHeaders()))
        .staticHeaderConfiguration(parser.parseStaticHeaderConfiguration(properties.getAddHeaders()))
        .build();
  }

}
