package com.wundermanthompson.markuply.examples.helloworldhttp.controller;

import com.wundermanthompson.markuply.engine.pipeline.http.HttpPipeline;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.HttpPageResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TemplatingController {

  private final HttpPipeline pageProcessor;

  public TemplatingController(HttpPipeline pageProcessor) {
    this.pageProcessor = pageProcessor;
  }

  @GetMapping(value = "/templating/{*pathToProcess}", produces = MediaType.TEXT_HTML_VALUE)
  public Mono<String> genericPage(@PathVariable String pathToProcess) {
    return pageProcessor.render(pathToProcess).map(HttpPageResponse::getBody);
  }

}
