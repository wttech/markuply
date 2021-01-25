package com.wundermanthompson.markuply.engine.utils;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

@AllArgsConstructor(staticName = "instance")
public class ClasspathFileLoader {

  public Mono<String> load(String path) {
    return load(path, ClasspathFileLoader.class);
  }

  public Mono<String> load(String path, Class<?> clazz) {
    return Mono.fromCallable(() -> loadContent(path, clazz))
        .subscribeOn(Schedulers.boundedElastic());
  }

  public String loadBlocking(String path) {
    return loadBlocking(path, ClasspathFileLoader.class);
  }

  public String loadBlocking(String path, Class<?> clazz) {
    return loadContent(path, clazz);
  }

  private String loadContent(String path, Class<?> clazz) {
    try (InputStream inputStream = clazz.getResourceAsStream(path)) {
      return InputStreamUtils.readAll(inputStream);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

}
