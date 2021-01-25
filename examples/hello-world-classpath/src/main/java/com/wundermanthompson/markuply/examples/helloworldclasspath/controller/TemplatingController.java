package com.wundermanthompson.markuply.examples.helloworldclasspath.controller;

import com.wundermanthompson.markuply.engine.pipeline.classpath.ClasspathPipeline;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TemplatingController {

  private final ClasspathPipeline pageProcessor;

  public TemplatingController(ClasspathPipeline pageProcessor) {
    this.pageProcessor = pageProcessor;
  }

  @GetMapping(value = "/templating/{*pathToProcess}", produces = MediaType.TEXT_HTML_VALUE)
  public Mono<String> genericPage(@PathVariable String pathToProcess) {
    return pageProcessor.render(pathToProcess);
  }

}
