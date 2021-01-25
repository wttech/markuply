package com.wundermanthompson.markuply.javascript.context.provider;

import com.wundermanthompson.markuply.javascript.repository.ScriptProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

@Slf4j
public class CachedContextProvider implements ContextProvider {

  private final Mono<Context> cachedContext;

  CachedContextProvider(Mono<Context> cachedContext) {
    this.cachedContext = cachedContext;
  }

  public static CachedContextProvider of(ScriptProvider scriptProvider) {
    return new CachedContextProvider(createContextMono(scriptProvider));
  }

  private static Mono<Context> createContextMono(ScriptProvider scriptProvider) {
    return scriptProvider.getBundle().handle(new CacheContextOperator());
  }

  public Mono<Context> getContext() {
    return cachedContext;
  }

  private static class CacheContextOperator
      implements BiConsumer<String, SynchronousSink<Context>> {

    private final ContextFactory contextFactory = ContextFactory.instance();
    private final AtomicReference<HashedContext> hashedContext = new AtomicReference<>();

    @Override
    public void accept(String script, SynchronousSink<Context> sink) {
      int newScriptHash = script.hashCode();
      HashedContext newContext =
          hashedContext.updateAndGet(
              currentHashedContext -> {
                if (currentHashedContext == null
                    || currentHashedContext.getScriptHash() != newScriptHash) {
                  log.debug("GraalJS context to be recreated");
                  return HashedContext.of(contextFactory.createContext(script), newScriptHash);
                }
                log.debug("GraalJS cached context returned");
                return currentHashedContext;
              });
      sink.next(newContext.getContext());
    }
  }

  @AllArgsConstructor(staticName = "of")
  @Getter
  private static class HashedContext {

    private final Context context;
    private final int scriptHash;
  }
}
