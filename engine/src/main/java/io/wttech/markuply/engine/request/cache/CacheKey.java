package io.wttech.markuply.engine.request.cache;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@AllArgsConstructor(staticName = "of")
public class CacheKey {

  private final Class<?> clazz;
  private final String method;
  private final Object[] arguments;

}
