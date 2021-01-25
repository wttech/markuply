package com.wundermanthompson.markuply.engine.renderer;

import com.wundermanthompson.markuply.engine.template.graph.RenderFunction;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor(staticName = "instance")
public class DirectRenderFunctionProvider implements RenderFunctionProvider {

  private final RenderFunctionFactory factory;

  @Override
  public Mono<RenderFunction> get(String path, String input) {
    return Mono.just(factory.create(input));
  }

}
