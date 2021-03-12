package io.wttech.markuply.engine.pipeline.repository;

import io.wttech.markuply.engine.MarkuplyException;

public class PageRepositoryException extends MarkuplyException {

  public PageRepositoryException() {
  }

  public PageRepositoryException(String message) {
    super(message);
  }

  public PageRepositoryException(String message, Throwable cause) {
    super(message, cause);
  }

  public PageRepositoryException(Throwable cause) {
    super(cause);
  }

  public PageRepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
