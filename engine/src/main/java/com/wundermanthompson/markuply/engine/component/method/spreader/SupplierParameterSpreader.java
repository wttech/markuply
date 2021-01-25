package com.wundermanthompson.markuply.engine.component.method.spreader;

import com.wundermanthompson.markuply.engine.component.method.function.UnifiedSupplier;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor(staticName = "of")
public class SupplierParameterSpreader<T> implements ParameterSpreader<T> {

  private final UnifiedSupplier<T> supplier;

  @Override
  public T invoke(List<Object> arguments) {
    return supplier.apply();
  }
}
