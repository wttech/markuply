package com.wundermanthompson.markuply.engine.pipeline.http.processor.configuration;

import org.springframework.web.reactive.function.client.WebClient;

public interface HttpRepositoryConfigurator {

  HttpRepositoryConfigurator urlPrefix(String urlPrefix);

  HttpRepositoryConfigurator webClient(WebClient webClient);

}
