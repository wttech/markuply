package com.wundermanthompson.markuply.examples.pipelines.controller;

import com.wundermanthompson.markuply.engine.pipeline.http.HttpPipeline;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.HttpPageResponse;
import com.wundermanthompson.markuply.examples.pipelines.annotations.MainPipeline;
import com.wundermanthompson.markuply.examples.pipelines.annotations.SecondaryPipeline;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TemplatingController {

  private final HttpPipeline main;
  private final HttpPipeline secondary;

  public TemplatingController(@MainPipeline HttpPipeline main, @SecondaryPipeline HttpPipeline secondary) {
    this.main = main;
    this.secondary = secondary;
  }

  @GetMapping(value = "/main/{*pathToProcess}", produces = MediaType.TEXT_HTML_VALUE)
  public Mono<String> primaryPipelineEndpoint(@PathVariable String pathToProcess) {
    return main.render(pathToProcess).map(HttpPageResponse::getBody);
  }

  @GetMapping(value = "/secondary/{*pathToProcess}", produces = MediaType.TEXT_HTML_VALUE)
  public Mono<String> secondaryPipelineEndpoint(@PathVariable String pathToProcess) {
    return secondary.render(pathToProcess).map(HttpPageResponse::getBody);
  }

}
