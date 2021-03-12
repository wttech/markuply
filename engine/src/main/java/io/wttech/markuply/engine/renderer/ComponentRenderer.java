package io.wttech.markuply.engine.renderer;

import io.wttech.markuply.engine.component.MarkuplyComponentContext;
import io.wttech.markuply.engine.pipeline.context.PageContext;
import io.wttech.markuply.engine.renderer.error.ComponentErrorHandler;
import io.wttech.markuply.engine.renderer.missing.MissingComponentHandler;
import io.wttech.markuply.engine.renderer.registry.ComponentRegistry;
import io.wttech.markuply.engine.template.graph.NamedRenderFunctions;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Renders a component based on component definitions stored in component registry and handles the
 * missing component case.
 */
@Slf4j
@AllArgsConstructor(staticName = "of")
public class ComponentRenderer {

  @NonNull
  private final ComponentRegistry componentRegistry;
  @NonNull
  private final MissingComponentHandler missingComponentHandler;
  @NonNull
  private final ComponentErrorHandler errorHandler;

  public Mono<String> render(String componentId, String props, PageContext context, NamedRenderFunctions childrenRenderer) {
    return componentRegistry
        .find(componentId)
        .map(component -> {
          log.debug("Rendering component: {}. Props: {}. Context: {}.", componentId, props, context);
          return component.render(MarkuplyComponentContext.of(props, context, childrenRenderer));
        })
        .orElseGet(() -> missingComponentHandler.missingComponent(componentId, props, context))
        .onErrorResume(e -> errorHandler.handleError(e, componentId, props, context));
  }

}
