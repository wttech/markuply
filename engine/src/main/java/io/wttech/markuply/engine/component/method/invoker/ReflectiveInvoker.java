package io.wttech.markuply.engine.component.method.invoker;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.util.List;

@RequiredArgsConstructor(staticName = "of")
public class ReflectiveInvoker<T> implements MethodInvoker<T> {

  private final Object targetInstance;
  private final Method method;

  @Override
  public T invoke(List<Object> parameters) throws Exception {
    return (T) method.invoke(targetInstance, parameters.toArray());
  }

}
