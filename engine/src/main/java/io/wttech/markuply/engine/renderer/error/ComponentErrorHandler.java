package io.wttech.markuply.engine.renderer.error;

import io.wttech.markuply.engine.pipeline.context.PageContext;
import reactor.core.publisher.Mono;

/**
 *
 */
@FunctionalInterface
public interface ComponentErrorHandler {

  /**
   * Process the error and return a fallback value or throw a runtime exception to map the original one.
   *
   * @param e           exception that occured during component rendering
   * @param componentId component identifier
   * @param props       properties passed in data-props attribute
   * @param context     page context
   * @return fallback mono value, can be an error
   */
  Mono<String> handleError(Throwable e, String componentId, String props, PageContext context);

}
