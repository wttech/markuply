package io.wttech.markuply.engine.component.method.resolver;

import io.wttech.markuply.engine.component.MarkuplyComponentContext;

/**
 * interface for dynamic argument resolving for Markuply component methods.
 */
public interface MethodArgumentResolver {

  Object resolve(MarkuplyComponentContext context);

}
