package com.wundermanthompson.markuply.javascript.context.provider;

import lombok.AllArgsConstructor;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;

import java.io.IOException;
import java.io.UncheckedIOException;

@AllArgsConstructor(staticName = "instance")
public class ContextFactory {

  private static final String LANGUAGE = "js";
  private static final String BUNDLE_NAME = "bundle.js";

  public Context createContext(String script) {
    try {
      Context context = Context.create();
      context.eval(Source.newBuilder(LANGUAGE, script, BUNDLE_NAME).build());
      return context;
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

}
