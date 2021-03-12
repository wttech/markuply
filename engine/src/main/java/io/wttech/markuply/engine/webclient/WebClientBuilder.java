package io.wttech.markuply.engine.webclient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

public class WebClientBuilder {

  private Integer connectTimeoutMillis;
  private Long readTimeoutMillis;
  private Integer maxSize;
  private boolean gzipEnabled = false;

  public static WebClientBuilder instance() {
    return new WebClientBuilder();
  }

  public WebClientBuilder connectTimeoutMillis(int connectTimeoutMillis) {
    this.connectTimeoutMillis = connectTimeoutMillis;
    return this;
  }

  public WebClientBuilder readTimeoutMillis(long readTimeoutMillis) {
    this.readTimeoutMillis = readTimeoutMillis;
    return this;
  }

  public WebClientBuilder maxSize(int maxSize) {
    this.maxSize = maxSize;
    return this;
  }

  public WebClientBuilder enableGzip() {
    this.gzipEnabled = true;
    return this;
  }

  public WebClient build() {
    Builder builder = WebClient.builder();
    configureTimeouts(builder);
    configureMaxSize(builder);
    return builder.build();
  }

  private void configureTimeouts(Builder builder) {
    // create reactor netty HTTP client
    HttpClient httpClient = HttpClient.create();
    httpClient = httpClient.compress(gzipEnabled);
    httpClient = configureTcpClient(httpClient);
    // create a client http connector using above http client
    ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
    // use this configured http connector to build the web client
    builder.clientConnector(connector);
  }

  private void configureMaxSize(Builder builder) {
    if (maxSize != null) {
      builder
          .exchangeStrategies(ExchangeStrategies
              .builder()
              .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(maxSize))
              .build());
    }
  }

  private HttpClient configureTcpClient(HttpClient tcpClient) {
    HttpClient result = tcpClient;
    if (connectTimeoutMillis != null) {
      result = result
          .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMillis);
    }
    if (readTimeoutMillis != null) {
      result = result.doOnConnected(conn -> conn
          .addHandlerLast(new ReadTimeoutHandler(readTimeoutMillis, TimeUnit.MILLISECONDS)));
    }
    return result;
  }

}
