package com.wundermanthompson.markuply.engine.renderer;

import com.wundermanthompson.markuply.engine.template.graph.RenderFunction;
import reactor.core.publisher.Mono;

public interface RenderFunctionProvider {

  Mono<RenderFunction> get(String path, String input);

}
