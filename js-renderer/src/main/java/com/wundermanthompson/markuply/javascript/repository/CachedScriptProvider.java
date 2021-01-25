package com.wundermanthompson.markuply.javascript.repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RequiredArgsConstructor
public class CachedScriptProvider implements ScriptProvider {

  @NonNull
  private final ScriptProvider originalScriptProvider;
  @NonNull
  private final Duration ttl;

  public static CachedScriptProvider of(ScriptProvider scriptProvider) {
    return new CachedScriptProvider(scriptProvider, null);
  }

  public static CachedScriptProvider of(ScriptProvider scriptProvider, Duration ttl) {
    return new CachedScriptProvider(scriptProvider, ttl);
  }

  @Override
  public Mono<String> getBundle() {
    return ttl != null
        ? originalScriptProvider.getBundle().cache(ttl)
        : originalScriptProvider.getBundle().cache();
  }

}
