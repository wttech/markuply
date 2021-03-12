package io.wttech.markuply.examples.openlibrary.controller.exception;

import java.time.Instant;

public class ErrorResult {

  private String message;
  private Instant timestamp;

  public ErrorResult(String message, Instant timestamp) {
    this.message = message;
    this.timestamp = timestamp;
  }

  public static ErrorResult of(String message) {
    return new ErrorResult(message, Instant.now());
  }

  public String getMessage() {
    return message;
  }

  public Instant getTimestamp() {
    return timestamp;
  }
}
