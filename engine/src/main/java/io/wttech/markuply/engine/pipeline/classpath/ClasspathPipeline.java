package io.wttech.markuply.engine.pipeline.classpath;

import io.wttech.markuply.engine.pipeline.context.PageContext;
import reactor.core.publisher.Mono;

/**
 * Retrieves HTML markup from the classpath and processes component placeholders found in the markup.
 */
public interface ClasspathPipeline {

  Mono<String> render(String path);

  Mono<String> render(String path, PageContext context);

}
