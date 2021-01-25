package com.wundermanthompson.markuply.javascript.repository;

import com.wundermanthompson.markuply.javascript.JavascriptRendererException;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Builder(builderClassName = "Builder")
public class ExternalScriptProvider implements ScriptProvider {

  private final WebClient webClient;
  private final URI uri;

  public Mono<String> getBundle() {
    return webClient.get()
        .uri(uri)
        .retrieve()
        .onStatus(status -> !status.equals(HttpStatus.OK), response -> Mono.error(new JavascriptRendererException("Could not retrieve script. Server did not return 200 OK.")))
        .bodyToMono(String.class);
  }


}
