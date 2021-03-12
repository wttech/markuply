package io.wttech.markuply.engine.component;

import io.wttech.markuply.engine.MarkuplyException;

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
