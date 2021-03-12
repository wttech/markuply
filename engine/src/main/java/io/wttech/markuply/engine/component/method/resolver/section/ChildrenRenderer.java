package io.wttech.markuply.engine.component.method.resolver.section;

import io.wttech.markuply.engine.pipeline.context.PageContext;
import reactor.core.publisher.Mono;

/**
 * Exposes access to component inner content:
 * <ul>
 *   <li>default, unnamed section</li>
 *   <li>any named section</li>
 * </ul>
 */
public interface ChildrenRenderer {

  /**
   * Evaluates the default inner content section using the current page context.
   *
   * @return evaluated HTML content
   */
  Mono<String> render();

  /**
   * Evaluates a named inner content section using the current page context.
   *
   * @param name section to render
   * @return evaluated HTML content
   */
  Mono<String> render(String name);

  /**
   * Evaluates the default inner content section using the provided page context.
   *
   * @param context custom context passed to section renderer
   * @return evaluated HTML content
   */
  Mono<String> render(PageContext context);

  /**
   * Evaluates a named inner content section using the provided page context.
   *
   * @param name section to render
   * @param context custom context passed to section renderer
   * @return evaluated HTML content
   */
  Mono<String> render(String name, PageContext context);

}
