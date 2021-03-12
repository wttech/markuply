package io.wttech.markuply.examples.openlibrary;

import io.wttech.graal.templating.javascript.JavascriptRenderer;
import io.wttech.graal.templating.spring.javascript.JavascriptRendererSpringConfigurator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Configuration
@ComponentScan
public class OpenBookApplicationConfiguration {

  @Bean
  JavascriptRenderer javascriptRenderer(JavascriptRendererSpringConfigurator configurator, WebClient webClient) {
    return configurator.externalScript(builder -> builder.webClient(webClient)
        .uri(URI.create("http://localhost:3000/bundle.js"))
        .build()
    ).buildDevelopment();
  }

}
