package com.wundermanthompson.markuply.javascript.repository;

import com.wundermanthompson.markuply.javascript.JavascriptRendererException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.io.UncheckedIOException;

@RequiredArgsConstructor(staticName = "of")
public class ClasspathScriptProvider implements ScriptProvider {

  @NonNull
  private final String resourcePath;
  private final ClasspathFileLoader classpathFileLoader = ClasspathFileLoader.instance();

  public Mono<String> getBundle() {
    try {
      return classpathFileLoader.load(resourcePath, ClasspathScriptProvider.class);
    } catch (UncheckedIOException e) {
      throw new JavascriptRendererException("Could not retrieve script", e);
    }
  }

}
