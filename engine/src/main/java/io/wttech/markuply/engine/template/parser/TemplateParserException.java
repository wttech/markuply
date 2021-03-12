package io.wttech.markuply.engine.template.parser;

import io.wttech.markuply.engine.MarkuplyException;

public class TemplateParserException extends MarkuplyException {

  public TemplateParserException() {
  }

  public TemplateParserException(String message) {
    super(message);
  }

  public TemplateParserException(String message, Throwable cause) {
    super(message, cause);
  }

  public TemplateParserException(Throwable cause) {
    super(cause);
  }

  public TemplateParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
