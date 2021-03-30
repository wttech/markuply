package io.wttech.markuply.engine.component.method.invoker;

import java.util.List;

public interface MethodInvoker<T> {

  T invoke(List<Object> parameters) throws Exception;

}
