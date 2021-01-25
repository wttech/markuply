package com.wundermanthompson.markuply.engine.renderer.cache;

import com.wundermanthompson.markuply.engine.renderer.RenderFunctionProvider;
import com.wundermanthompson.markuply.engine.template.graph.RenderFunction;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor(staticName = "instance")
public class CachedRenderFunctionProvider implements RenderFunctionProvider {

  private final RenderFunctionCache cache;

  @Override
  public Mono<RenderFunction> get(String path, String input) {
    return cache.get(path, input);
  }

}
