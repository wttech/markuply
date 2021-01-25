package com.wundermanthompson.markuply.engine.component.method.resolver;

import java.lang.reflect.Parameter;
import java.util.Optional;

/**
 * Factory for argument resolvers.
 * <p>
 * Factory can extract all necessary information using reflections on startup and based on it a final resolver is returned.
 */
public interface MethodArgumentResolverFactory {

  /**
   * @param parameter method argument of the component method
   * @return empty optional if parameter is not supported
   */
  Optional<MethodArgumentResolver> createResolver(Parameter parameter);

}
