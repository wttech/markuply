package io.wttech.markuply.engine.component.method.spreader;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.function.Function;

@AllArgsConstructor(staticName = "of")
public class FunctionParameterSpreader<T> implements ParameterSpreader<T> {

  private final Function<Object, T> function;

  @Override
  public T invoke(List<Object> arguments) {
    return function.apply(arguments.get(0));
  }
}
