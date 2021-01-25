package com.wundermanthompson.markuply.engine.renderer.error;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComponentErrorHandlers {

  public static LogOrPropagateErrorHandler logAll() {
    return new LogOrPropagateErrorHandler(new ArrayList<>());
  }

  public static LogOrPropagateErrorHandler logAllExcept(Class<? extends Throwable>... typesToPropagate) {
    return new LogOrPropagateErrorHandler(Arrays.asList(typesToPropagate));
  }

  public static LogOrPropagateErrorHandler logAllExcept(List<Class<? extends Throwable>> typesToPropagate) {
    return new LogOrPropagateErrorHandler(typesToPropagate);
  }

}
