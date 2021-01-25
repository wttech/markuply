package com.wundermanthompson.markuply.examples.pipelines;

import com.wundermanthompson.markuply.engine.pipeline.http.HttpPipeline;
import com.wundermanthompson.markuply.engine.pipeline.http.processor.configuration.HttpPageProcessorConfigurator;
import com.wundermanthompson.markuply.engine.renderer.RenderFunctionFactory;
import com.wundermanthompson.markuply.examples.pipelines.annotations.MainPipeline;
import com.wundermanthompson.markuply.examples.pipelines.annotations.SecondaryPipeline;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Configuration
public class MultiplePipelinesConfiguration {

  private final List<String> requestHeadersToProxy = Arrays.asList(
      "Accept.*",
      "Authorization",
      "Connection",
      "Cookie",
      "Date",
      "Edge.*",
      "Origin",
      "Pragma",
      "Proxy-Authorization",
      "Surrogate.*",
      "User-Agent",
      "Via",
      "X-.*"
  );

  private final List<String> responseHeadersToProxy = Arrays.asList(
      "Access-Control-Allow-Origin",
      "Allow",
      "Cache-Control",
      "Content-Disposition",
      "Content-Encoding",
      "Content-Language",
      "Content-Location",
      "Content-MD5",
      "Content-Range",
      "Content-Type",
      "Content-Length",
      "Content-Security-Policy",
      "Date",
      "Edge-Control",
      "ETag",
      "Expires",
      "Last-Modified",
      "Location",
      "Pragma",
      "Proxy-Authenticate",
      "Server",
      "Set-Cookie",
      "Status",
      "Surrogate-Control",
      "Vary",
      "Via",
      "X-Frame-Options",
      "X-XSS-Protection",
      "X-Content-Type-Options",
      "X-UA-Compatible",
      "X-Request-ID",
      "X-Server"
  );

  @Bean
  @MainPipeline
  HttpPipeline mainPipeline(WebClient webClient, RenderFunctionFactory factory) {
    HttpPageProcessorConfigurator builder = HttpPageProcessorConfigurator.instance(factory);
    return builder
        .repository(repository -> repository.urlPrefix("http://localhost:8082/main").webClient(webClient))
        .requestProxy(proxy -> proxy.proxyHeader(requestHeadersToProxy).setHeader("X-User-Agent", "Markuply Main"))
        .responseProxy(proxy -> proxy.proxyHeader(responseHeadersToProxy).setHeader("Server", "Markuply Main"))
        .renderFunctionCache(caffeine -> caffeine.maximumSize(1000).expireAfterAccess(Duration.ofSeconds(5)))
        .build();
  }

  @Bean
  @SecondaryPipeline
  HttpPipeline secondaryPipeline(WebClient webClient, RenderFunctionFactory factory) {
    HttpPageProcessorConfigurator builder = HttpPageProcessorConfigurator.instance(factory);
    return builder
        .repository(repository -> repository.urlPrefix("http://localhost:8082/secondary").webClient(webClient))
        .requestProxy(proxy -> proxy.proxyHeader(requestHeadersToProxy).setHeader("X-User-Agent", "Markuply Secondary"))
        .responseProxy(proxy -> proxy.proxyHeader(responseHeadersToProxy).setHeader("Server", "Markuply Secondary"))
        .renderFunctionCache(caffeine -> caffeine.maximumSize(1000).expireAfterAccess(Duration.ofSeconds(5)))
        .build();
  }


}
