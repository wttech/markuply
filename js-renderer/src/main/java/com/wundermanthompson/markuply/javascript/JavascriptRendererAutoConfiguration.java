package com.wundermanthompson.markuply.javascript;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wundermanthompson.markuply.engine.MarkuplyAutoConfiguration;
import com.wundermanthompson.markuply.engine.configuration.ConditionalOnConfigurationProperties;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@Import({JavascriptRendererBaseConfiguration.class})
@AutoConfigureAfter(MarkuplyAutoConfiguration.class)
public class JavascriptRendererAutoConfiguration {

  private static final String DEFAULT_SCRIPT_PATH = "/bundle.js";

  @Bean
  @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  JavascriptRendererConfigurator provideJavascriptRendererDsl(ObjectMapper objectMapper) {
    return JavascriptRendererConfigurator.instance()
        .classpathScript(DEFAULT_SCRIPT_PATH)
        .objectMapper(objectMapper);
  }

  @Bean
  @ConditionalOnConfigurationProperties(prefix = JavascriptRendererSpringProperties.PREFIX, targetClass = JavascriptRendererSpringProperties.class)
  @ConditionalOnMissingBean(JavascriptRenderer.class)
  JavascriptRenderer javascriptRendererFromProperties(JavascriptRendererSpringProperties properties, ObjectMapper mapper, WebClient webClient) {
    try {
      URI parsedUri = new URI(properties.getBundle());
      JavascriptRendererConfigurator configurator = JavascriptRendererConfigurator.instance()
          .objectMapper(mapper)
          .externalScript(builder -> builder
              .webClient(webClient)
              .uri(parsedUri));
      return properties.isProduction() ? configurator.buildProduction() : configurator.buildDevelopment();
    } catch (URISyntaxException e) {
      throw new JavascriptRendererException("markuply.javascript.bundle property contains invalid URL");
    }
  }

}
