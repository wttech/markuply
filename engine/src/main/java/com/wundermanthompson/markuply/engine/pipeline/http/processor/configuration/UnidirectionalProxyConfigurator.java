package com.wundermanthompson.markuply.engine.pipeline.http.processor.configuration;

import com.wundermanthompson.markuply.engine.pipeline.http.proxy.configuration.HeaderProxyConfiguration;
import com.wundermanthompson.markuply.engine.pipeline.http.proxy.configuration.StaticHeaderConfiguration;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor(staticName = "instance")
public class UnidirectionalProxyConfigurator implements ProxyConfigurator {

  private final HeaderProxyConfiguration.Builder headerProxyBuilder = HeaderProxyConfiguration.builder();
  private final StaticHeaderConfiguration.Builder staticHeaderBuilder = StaticHeaderConfiguration.builder();

  public HeaderProxyConfiguration buildHeaderProxyConfiguration() {
    return headerProxyBuilder.build();
  }

  public StaticHeaderConfiguration buildStaticHeaderConfiguration() {
    return staticHeaderBuilder.build();
  }

  @Override
  public UnidirectionalProxyConfigurator proxyHeader(String headerPattern) {
    headerProxyBuilder.allow(headerPattern);
    return this;
  }

  @Override
  public UnidirectionalProxyConfigurator proxyHeader(Collection<String> headerPattern) {
    headerPattern.forEach(this::proxyHeader);
    return this;
  }

  @Override
  public UnidirectionalProxyConfigurator setHeader(String name, String value) {
    staticHeaderBuilder.setHeader(name, value);
    return this;
  }

  @Override
  public UnidirectionalProxyConfigurator setHeader(String name, List<String> values) {
    staticHeaderBuilder.setHeader(name, values);
    return this;
  }

}
