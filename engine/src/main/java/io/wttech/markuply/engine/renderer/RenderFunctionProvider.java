package io.wttech.markuply.engine.renderer;

import io.wttech.markuply.engine.template.graph.RenderFunction;
import reactor.core.publisher.Mono;

public interface RenderFunctionProvider {

  Mono<RenderFunction> get(String path, String input);

}
