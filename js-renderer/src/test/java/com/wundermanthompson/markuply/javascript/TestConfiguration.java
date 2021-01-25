package com.wundermanthompson.markuply.javascript;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({TestComponents.class, JavascriptRendererAutoConfiguration.class})
public class TestConfiguration {

  @Bean
  JavascriptRenderer renderer(JavascriptRendererConfigurator configurator) {
    return configurator.buildDevelopment();
  }

}
