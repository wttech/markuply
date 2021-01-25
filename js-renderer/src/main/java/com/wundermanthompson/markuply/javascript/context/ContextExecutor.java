package com.wundermanthompson.markuply.javascript.context;

import org.graalvm.polyglot.Context;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface ContextExecutor {

  <T> Mono<T> withContext(Function<Context, T> contextMapper);

}
