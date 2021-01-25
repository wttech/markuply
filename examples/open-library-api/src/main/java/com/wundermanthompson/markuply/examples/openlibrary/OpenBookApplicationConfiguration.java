package com.wundermanthompson.markuply.examples.openlibrary;

import com.wundermanthompson.markuply.engine.webclient.WebClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan
public class OpenBookApplicationConfiguration {

  @Bean
  WebClient provideDefaultWebClient() {
    return WebClientBuilder.instance()
        .readTimeoutMillis(15 * 1000)
        .connectTimeoutMillis(15 * 1000)
        .maxSize(5 * 1024 * 2014)
        .build();
  }

}
