package com.wundermanthompson.markuply.engine.pipeline.classpath;

import com.wundermanthompson.markuply.engine.utils.ClasspathFileLoader;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Retrieves HTML content from a classpath resource.
 */
@RequiredArgsConstructor(staticName = "instance")
public class ClasspathPageRepository {

  private final ClasspathFileLoader classpathFileLoader = ClasspathFileLoader.instance();

  public Mono<String> getPage(String path) {
    return classpathFileLoader.load(path, ClasspathPageRepository.class);
  }

}
