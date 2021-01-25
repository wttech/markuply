package com.wundermanthompson.markuply.engine.renderer.cache;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.wundermanthompson.markuply.engine.renderer.RenderFunctionFactory;
import com.wundermanthompson.markuply.engine.template.graph.RenderFunction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Cache for RenderFunction objects. Checks the provided key as well as the input itself.
 * <p>
 * When a new input is provided for the same key (path) then a new entry is added to the cache.
 */
@RequiredArgsConstructor
public class RenderFunctionCache {

  private final AsyncLoadingCache<RenderFunctionKey, RenderFunction> cache;

  public static RenderFunctionCache instance(RenderFunctionFactory factory, Caffeine<Object, Object> cacheConfiguration) {
    return new RenderFunctionCache(cacheConfiguration.buildAsync(key -> factory.create(key.input)));
  }

  public Mono<RenderFunction> get(Object key, String input) {
    RenderFunctionKey realKey = RenderFunctionKey.instance(key, input);
    CompletableFuture<RenderFunction> future = cache.get(realKey);
    return Mono.fromFuture(future);
  }

  @RequiredArgsConstructor(staticName = "instance")
  @Getter
  public static class RenderFunctionKey {

    private final Object key;
    private final String input;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      RenderFunctionKey that = (RenderFunctionKey) o;
      return key.equals(that.key) &&
          input.equals(that.input);
    }

    @Override
    public int hashCode() {
      return Objects.hash(key);
    }
  }

}
