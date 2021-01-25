package com.wundermanthompson.markuply.engine.template.graph;

import com.wundermanthompson.markuply.engine.pipeline.context.PageContext;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface RenderFunction {

  RenderFunction NO_OP = context -> Mono.empty();

  Mono<String> render(PageContext pageContext);

}
