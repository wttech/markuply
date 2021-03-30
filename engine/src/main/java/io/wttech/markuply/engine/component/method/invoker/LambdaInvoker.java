package io.wttech.markuply.engine.component.method.invoker;

import io.wttech.markuply.engine.component.method.spreader.ParameterSpreader;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(staticName = "of")
public class LambdaInvoker<T> implements MethodInvoker<T> {

  private final ParameterSpreader<T> spreader;

  @Override
  public T invoke(List<Object> parameters) throws Exception {
    return spreader.invoke(parameters);
  }

}
