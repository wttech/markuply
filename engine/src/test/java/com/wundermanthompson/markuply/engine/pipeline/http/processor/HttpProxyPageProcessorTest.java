package com.wundermanthompson.markuply.engine.pipeline.http.processor;

import com.wundermanthompson.markuply.engine.pipeline.http.HttpPipeline;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.HttpPageResponse;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.context.Context;

import javax.inject.Inject;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.web.filter.reactive.ServerWebExchangeContextFilter.EXCHANGE_CONTEXT_ATTRIBUTE;

@SpringBootTest(classes = {TestConfiguration.class})
@TestPropertySource("classpath:configurations/httpRepository.properties")
@EnableAutoConfiguration
@EnableConfigurationProperties
public class HttpProxyPageProcessorTest {

  private static final String DEFAULT_BODY = "<html><head></head><body></body></html>";

  private static final String CUSTOM_HEADER = "X-Custom-Header";
  private static final String CUSTOM_REQUEST_STATIC_HEADER = "X-User-Agent";
  private static final String CUSTOM_RESPONSE_STATIC_HEADER = "Server";
  private static final String NOT_PROXIED_CUSTOM_HEADER = "someOtherHeader";
  private static final String CUSTOM_REQUEST_HEADER_VALUE = "request";
  private static final String CUSTOM_RESPONSE_HEADER_VALUE = "response";

  static MockWebServer mockWebServer;

  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) throws IOException {
    registry.add("markuply.http.repository.urlPrefix", () -> "http://localhost:" + mockWebServer.getPort());
  }

  @BeforeAll
  static void beforeAll() throws IOException {
    mockWebServer = new MockWebServer();
    mockWebServer.start();
  }

  @AfterAll
  static void afterAll() throws IOException {
    mockWebServer.shutdown();
  }

  @Inject
  private HttpPipeline processor;

  @Test
  void testRequestHeaderIsProxied() {
    mockWebServer.setDispatcher(createRequestTestingDispatcher(CUSTOM_HEADER, CUSTOM_REQUEST_HEADER_VALUE));

    MockServerHttpRequest request = MockServerHttpRequest.get("/dummy.html").header(CUSTOM_HEADER, CUSTOM_REQUEST_HEADER_VALUE).build();
    MockServerWebExchange exchange = MockServerWebExchange.builder(request).build();

    Mono<HttpPageResponse> responseMono = processor.render("/dummy.html").contextWrite(Context.of(EXCHANGE_CONTEXT_ATTRIBUTE, exchange));

    verifyResponseCode(responseMono, 200);
  }

  @Test
  void staticRequestHeaderIsAdded() {
    mockWebServer.setDispatcher(createRequestTestingDispatcher(CUSTOM_REQUEST_STATIC_HEADER, "Markuply"));

    MockServerHttpRequest request = MockServerHttpRequest.get("/dummy.html").build();
    MockServerWebExchange exchange = MockServerWebExchange.builder(request).build();

    Mono<HttpPageResponse> responseMono = processor.render("/dummy.html").contextWrite(Context.of(EXCHANGE_CONTEXT_ATTRIBUTE, exchange));

    StepVerifier.create(responseMono)
        .expectNextMatches(response -> response.getStatusCode() == 200)
        .verifyComplete();
  }

  @Test
  void testRequestHeaderIsNotProxied() {
    mockWebServer.setDispatcher(createRequestTestingDispatcher(NOT_PROXIED_CUSTOM_HEADER, CUSTOM_REQUEST_HEADER_VALUE));

    MockServerHttpRequest request = MockServerHttpRequest.get("/dummy.html").header(NOT_PROXIED_CUSTOM_HEADER, CUSTOM_REQUEST_HEADER_VALUE).build();
    MockServerWebExchange exchange = MockServerWebExchange.builder(request).build();

    Mono<HttpPageResponse> responseMono = processor.render("/dummy.html").contextWrite(Context.of(EXCHANGE_CONTEXT_ATTRIBUTE, exchange));

    verifyResponseCode(responseMono, 400);
  }

  @Test
  void testResponseHeaderIsProxied() {
    mockWebServer.setDispatcher(createResponseTestingDispatcher(CUSTOM_HEADER, CUSTOM_RESPONSE_HEADER_VALUE));

    MockServerHttpRequest request = MockServerHttpRequest.get("/dummy.html").build();
    MockServerWebExchange exchange = MockServerWebExchange.builder(request).build();

    Mono<HttpPageResponse> responseMono = processor.render("/dummy.html").contextWrite(Context.of(EXCHANGE_CONTEXT_ATTRIBUTE, exchange));

    verifyResponseCode(responseMono, 200);

    assertThat(exchange.getResponse().getHeaders()).containsKey(CUSTOM_HEADER);
    assertThat(exchange.getResponse().getHeaders().get(CUSTOM_HEADER)).contains(CUSTOM_RESPONSE_HEADER_VALUE);
  }

  @Test
  void testStaticResponseHeaderIsAdded() {
    mockWebServer.setDispatcher(createResponseTestingDispatcher(CUSTOM_HEADER, CUSTOM_RESPONSE_HEADER_VALUE));

    MockServerHttpRequest request = MockServerHttpRequest.get("/dummy.html").build();
    MockServerWebExchange exchange = MockServerWebExchange.builder(request).build();

    Mono<HttpPageResponse> responseMono = processor.render("/dummy.html").contextWrite(Context.of(EXCHANGE_CONTEXT_ATTRIBUTE, exchange));

    verifyResponseCode(responseMono, 200);

    assertThat(exchange.getResponse().getHeaders()).containsKey(CUSTOM_RESPONSE_STATIC_HEADER);
    assertThat(exchange.getResponse().getHeaders().get(CUSTOM_RESPONSE_STATIC_HEADER)).contains("Markuply");
  }

  @Test
  void testResponseHeaderIsNotProxied() {
    mockWebServer.setDispatcher(createRequestTestingDispatcher(NOT_PROXIED_CUSTOM_HEADER, CUSTOM_REQUEST_HEADER_VALUE));

    MockServerHttpRequest request = MockServerHttpRequest.get("/dummy.html").build();
    MockServerWebExchange exchange = MockServerWebExchange.builder(request).build();
    Mono<HttpPageResponse> responseMono = processor.render("/dummy.html").contextWrite(Context.of(EXCHANGE_CONTEXT_ATTRIBUTE, exchange));

    verifyResponseCode(responseMono, 400);

    assertThat(exchange.getResponse().getHeaders().get(NOT_PROXIED_CUSTOM_HEADER)).isNullOrEmpty();
  }

  private void verifyResponseCode(Mono<HttpPageResponse> mono, int code) {
    StepVerifier.create(mono)
        .expectNextMatches(response -> response.getStatusCode() == code)
        .verifyComplete();
  }

  private Dispatcher createRequestTestingDispatcher(String expectedHeader, String expectedValue) {
    return new Dispatcher() {
      @NotNull
      @Override
      public MockResponse dispatch(@NotNull RecordedRequest recordedRequest) {
        String headerValue = recordedRequest.getHeader(expectedHeader);
        if (expectedValue.equals(headerValue)) {
          return createResponse(200);
        } else {
          return createResponse(400);
        }
      }
    };
  }

  private Dispatcher createResponseTestingDispatcher(String returnedHeader, String headerValue) {
    return new Dispatcher() {
      @NotNull
      @Override
      public MockResponse dispatch(@NotNull RecordedRequest recordedRequest) {
        return createResponse(200, returnedHeader, headerValue);
      }
    };
  }

  private MockResponse createResponse(int code, String returnedHeader, String headerValue) {
    return new MockResponse().setResponseCode(code).setHeader(returnedHeader, headerValue).setBody(DEFAULT_BODY);
  }

  private MockResponse createResponse(int code) {
    return new MockResponse().setResponseCode(code).setBody(DEFAULT_BODY);
  }

}
