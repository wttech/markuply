package com.wundermanthompson.markuply.engine.component;

import com.wundermanthompson.markuply.engine.MarkuplyException;

public class ComponentDefinitionException extends MarkuplyException {

  public ComponentDefinitionException() {
  }

  public ComponentDefinitionException(String message) {
    super(message);
  }

  public ComponentDefinitionException(String message, Throwable cause) {
    super(message, cause);
  }

  public ComponentDefinitionException(Throwable cause) {
    super(cause);
  }

  public ComponentDefinitionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
