package io.wttech.markuply.engine.renderer.missing;

import io.wttech.markuply.engine.pipeline.context.PageContext;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Missing component handler removing data-templating HTML element.
 */
@AllArgsConstructor(staticName = "instance")
public class NoopHandler implements MissingComponentHandler {

  @Override
  public Mono<String> missingComponent(String componentId, String props, PageContext context) {
    return Mono.just("");
  }
}
