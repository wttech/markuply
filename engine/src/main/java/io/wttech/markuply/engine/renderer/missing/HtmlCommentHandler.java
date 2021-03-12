package io.wttech.markuply.engine.renderer.missing;

import io.wttech.markuply.engine.pipeline.context.PageContext;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Missing component handler replacing data-templating HTML element with HTML comment.
 */
@AllArgsConstructor(staticName = "instance")
public class HtmlCommentHandler implements MissingComponentHandler {

  @Override
  public Mono<String> missingComponent(String componentId, String props, PageContext context) {
    return Mono.just("<!-- Component (" + componentId + ") is not registered. -->");
  }
}
