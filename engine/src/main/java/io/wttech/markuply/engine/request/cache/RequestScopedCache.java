package io.wttech.markuply.engine.request.cache;

import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor(staticName = "instance")
public class RequestScopedCache {
  /*
   * Since 'null' can be a valid result, we need a special object to indicate that
   * a method has never been executed before.
   *
   */
  public static final Object NONE = new Object();

  /*
   * Since ConcurrentHashMap doesn't accept 'null' as a valid value, we need a
   * special object to represent 'null'.
   *
   */
  public static final Object NULL = new Object();

  private final Map<CacheKey, Object> cache = new ConcurrentHashMap<>();

  public Object findCachedResult(CacheKey cacheKey) {
    return cache.getOrDefault(cacheKey, RequestScopedCache.NONE);
  }

  public Object save(CacheKey cacheKey, Object result) {
    return cache.computeIfAbsent(cacheKey, key -> result == null ? RequestScopedCache.NULL : result);
  }
}
