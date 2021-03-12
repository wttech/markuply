package io.wttech.markuply.engine.component.method.spreader;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.function.BiFunction;

@AllArgsConstructor(staticName = "of")
public class BiFunctionParameterSpreader<T> implements ParameterSpreader<T> {

  private final BiFunction<Object, Object, T> function;

  @Override
  public T invoke(List<Object> arguments) {
    return function.apply(arguments.get(0), arguments.get(1));
  }
}
