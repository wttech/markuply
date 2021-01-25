package com.wundermanthompson.markuply.engine.component.method.spreader;

import java.util.List;

/**
 * To be able to call Supplier, Function, BiFunction etc. with a common interface
 */
public interface ParameterSpreader<T> {

  T invoke(List<Object> arguments);

}
