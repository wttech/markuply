package io.wttech.markuply.engine.renderer.missing;

import io.wttech.markuply.engine.pipeline.context.PageContext;
import reactor.core.publisher.Mono;

/**
 * <p>
 * Defines what should happen if a requested component does not exist.
 * </p>
 */
public interface MissingComponentHandler {

  /**
   * @param componentId missing component ID
   * @param props       props passed to component in HTML
   * @param context     page context
   * @return Mono with content to replace the HTML component reference or an error to propagate
   */
  Mono<String> missingComponent(String componentId, String props, PageContext context);

}
