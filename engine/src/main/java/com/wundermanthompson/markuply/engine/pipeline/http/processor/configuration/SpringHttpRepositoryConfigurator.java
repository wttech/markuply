package com.wundermanthompson.markuply.engine.pipeline.http.processor.configuration;

import com.wundermanthompson.markuply.engine.MarkuplyException;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.BaseHttpPageRepository;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.HttpPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor(staticName = "instance")
public class SpringHttpRepositoryConfigurator implements HttpRepositoryConfigurator {

  private WebClient webClient;
  private String urlPrefix;

  public HttpPageRepository build() {
    if (webClient == null) {
      throw new MarkuplyException("HTTP repository cannot be built. Web client is not configured.");
    }
    String finalPrefix = urlPrefix != null
        ? urlPrefix
        : "";
    return BaseHttpPageRepository.of(webClient, finalPrefix);
  }

  @Override
  public SpringHttpRepositoryConfigurator urlPrefix(String urlPrefix) {
    this.urlPrefix = urlPrefix;
    return this;
  }

  @Override
  public SpringHttpRepositoryConfigurator webClient(WebClient webClient) {
    this.webClient = webClient;
    return this;
  }

}
