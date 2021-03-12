package io.wttech.markuply.engine;

public class MarkuplyException extends RuntimeException {

  public MarkuplyException() {
  }

  public MarkuplyException(String message) {
    super(message);
  }

  public MarkuplyException(String message, Throwable cause) {
    super(message, cause);
  }

  public MarkuplyException(Throwable cause) {
    super(cause);
  }

  public MarkuplyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
