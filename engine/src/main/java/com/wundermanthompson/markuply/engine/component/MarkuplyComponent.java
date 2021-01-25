package com.wundermanthompson.markuply.engine.component;

import reactor.core.publisher.Mono;

/**
 * <p>
 * Basic interface marking a templating component.
 * </p>
 * <p>
 * Receives raw string properties passed in data-props attribute in HTML and full PageContext.
 * </p>
 * <p>
 * Use {@link Markuply} annotation to define component ID. Otherwise the lower camelcase class name will be used.
 * </p>
 */
public interface MarkuplyComponent {

  Mono<String> render(MarkuplyComponentContext context);

}
