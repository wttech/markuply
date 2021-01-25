package com.wundermanthompson.markuply.javascript.context;

import com.wundermanthompson.markuply.javascript.context.provider.ContextProvider;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import reactor.core.publisher.Mono;
import reactor.pool.*;

import java.util.function.BiPredicate;
import java.util.function.Function;

public class ContextPool implements ContextExecutor {

  private final Pool<Context> contexts;

  ContextPool(Pool<Context> contexts) {
    this.contexts = contexts;
  }

  public static ContextPool development(ContextProvider contextProvider) {
    return builder()
        .contextProvider(contextProvider)
        .singular()
        .alwaysEvict()
        .build();
  }

  public static ContextPool production(ContextProvider contextProvider) {
    return builder()
        .contextProvider(contextProvider)
        .perEachLogicalCore()
        .build();
  }

  public static Builder builder() {
    return new Builder();
  }

  public <T> Mono<T> withContext(Function<Context, T> mapFunction) {
    return Mono.usingWhen(contexts.acquire(),
        pooledContext -> Mono.just(mapFunction.apply(pooledContext.poolable())),
        PooledRef::release);
  }

  @Slf4j
  public static class Builder {

    private static final int DEFAULT_SIZE = Runtime.getRuntime().availableProcessors();

    private ContextProvider contextProvider;
    private Integer size;
    private BiPredicate<Context, PooledRefMetadata> evictionPredicate;

    Builder contextProvider(ContextProvider contextProvider) {
      this.contextProvider = contextProvider;
      return this;
    }

    public Builder size(int size) {
      this.size = size;
      return this;
    }

    public Builder singular() {
      return size(1);
    }

    public Builder perEachLogicalCore() {
      return size(DEFAULT_SIZE);
    }

    public Builder alwaysEvict() {
      evictionPredicate = (context, pooledData) -> true;
      return this;
    }

    public ContextPool build() {
      int finalSize = size != null ? size : DEFAULT_SIZE;
      PoolBuilder<Context, PoolConfig<Context>> builder = PoolBuilder
          .from(contextProvider.getContext())
          .sizeBetween(finalSize, finalSize);
      if (evictionPredicate != null) {
        builder.evictionPredicate(evictionPredicate);
      }
      Pool<Context> contextPool = builder.buildPool();
      return new ContextPool(contextPool);
    }

  }

}
