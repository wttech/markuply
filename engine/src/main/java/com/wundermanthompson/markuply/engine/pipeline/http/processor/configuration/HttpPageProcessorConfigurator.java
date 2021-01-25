package com.wundermanthompson.markuply.engine.pipeline.http.processor.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.wundermanthompson.markuply.engine.MarkuplyException;
import com.wundermanthompson.markuply.engine.pipeline.http.HttpPipeline;
import com.wundermanthompson.markuply.engine.pipeline.http.processor.BaseHttpPageProcessor;
import com.wundermanthompson.markuply.engine.pipeline.http.processor.SpringProxyHttpPageProcessor;
import com.wundermanthompson.markuply.engine.pipeline.http.proxy.request.PageRequestConfigurableEnricher;
import com.wundermanthompson.markuply.engine.pipeline.http.proxy.request.ReactiveRequestEnricher;
import com.wundermanthompson.markuply.engine.pipeline.http.proxy.response.PageResponseConfigurableEnricher;
import com.wundermanthompson.markuply.engine.pipeline.http.proxy.response.PageResponseEnricher;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.EnrichedHttpPageRepository;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.HttpPageRepository;
import com.wundermanthompson.markuply.engine.renderer.DirectRenderFunctionProvider;
import com.wundermanthompson.markuply.engine.renderer.RenderFunctionFactory;
import com.wundermanthompson.markuply.engine.renderer.RenderFunctionProvider;
import com.wundermanthompson.markuply.engine.renderer.cache.CachedRenderFunctionProvider;
import com.wundermanthompson.markuply.engine.renderer.cache.RenderFunctionCache;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor(staticName = "instance")
public class HttpPageProcessorConfigurator {

  private final RenderFunctionFactory renderFunctionFactory;

  private RenderFunctionProvider renderFunctionProvider;
  private HttpPageRepository httpPageRepository;
  private ReactiveRequestEnricher requestProxy;
  private PageResponseEnricher responseProxy;

  public HttpPipeline build() {
    if (httpPageRepository == null) {
      throw new MarkuplyException("Pipeline cannot be built. Repository has not been configured.");
    }
    if (renderFunctionProvider == null) {
      renderFunctionProvider = DirectRenderFunctionProvider.instance(renderFunctionFactory);
    }
    HttpPageRepository finalRepository = requestProxy != null
        ? EnrichedHttpPageRepository.instance(httpPageRepository, requestProxy)
        : httpPageRepository;
    HttpPipeline baseProcessor = BaseHttpPageProcessor.of(finalRepository, renderFunctionProvider);
    return responseProxy != null
        ? SpringProxyHttpPageProcessor.instance(baseProcessor, responseProxy)
        : baseProcessor;
  }

  public HttpPageProcessorConfigurator renderFunctionCache(Consumer<Caffeine<Object, Object>> configurer) {
    Caffeine<Object, Object> cacheBuilder = Caffeine.newBuilder();
    configurer.accept(cacheBuilder);
    RenderFunctionCache cache = RenderFunctionCache.instance(renderFunctionFactory, cacheBuilder);
    renderFunctionProvider = CachedRenderFunctionProvider.instance(cache);
    return this;
  }

  public HttpPageProcessorConfigurator renderFunctionCache(RenderFunctionCache cache) {
    renderFunctionProvider = CachedRenderFunctionProvider.instance(cache);
    return this;
  }

  public HttpPageProcessorConfigurator repository(Consumer<HttpRepositoryConfigurator> configurer) {
    SpringHttpRepositoryConfigurator repositoryBuilder = SpringHttpRepositoryConfigurator.instance();
    configurer.accept(repositoryBuilder);
    httpPageRepository = repositoryBuilder.build();
    return this;
  }

  public HttpPageProcessorConfigurator requestProxy(Consumer<ProxyConfigurator> configurer) {
    UnidirectionalProxyConfigurator requestProxyBuilder = UnidirectionalProxyConfigurator.instance();
    configurer.accept(requestProxyBuilder);
    requestProxy = PageRequestConfigurableEnricher.builder()
        .headerProxyConfiguration(requestProxyBuilder.buildHeaderProxyConfiguration())
        .staticHeaderConfiguration(requestProxyBuilder.buildStaticHeaderConfiguration())
        .build();
    return this;
  }

  public HttpPageProcessorConfigurator responseProxy(Consumer<ProxyConfigurator> configurer) {
    UnidirectionalProxyConfigurator requestProxyBuilder = UnidirectionalProxyConfigurator.instance();
    configurer.accept(requestProxyBuilder);
    responseProxy = PageResponseConfigurableEnricher.builder()
        .headerProxyConfiguration(requestProxyBuilder.buildHeaderProxyConfiguration())
        .staticHeaderConfiguration(requestProxyBuilder.buildStaticHeaderConfiguration())
        .build();
    return this;
  }

}
