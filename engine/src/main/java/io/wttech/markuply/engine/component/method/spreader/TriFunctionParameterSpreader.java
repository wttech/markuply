package io.wttech.markuply.engine.component.method.spreader;

import io.wttech.markuply.engine.component.method.function.TriFunction;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor(staticName = "of")
public class TriFunctionParameterSpreader<T> implements ParameterSpreader<T> {

  private final TriFunction<Object, Object, Object, T> function;

  @Override
  public T invoke(List<Object> arguments) {
    return function.apply(arguments.get(0), arguments.get(1), arguments.get(2));
  }
}
