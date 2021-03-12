package io.wttech.markuply.engine.pipeline.http.processor.configuration;

import java.util.Collection;
import java.util.List;

public interface ProxyConfigurator {

  ProxyConfigurator proxyHeader(String headerPattern);

  ProxyConfigurator proxyHeader(Collection<String> headerPattern);

  ProxyConfigurator setHeader(String name, String value);

  ProxyConfigurator setHeader(String name, List<String> values);

}
