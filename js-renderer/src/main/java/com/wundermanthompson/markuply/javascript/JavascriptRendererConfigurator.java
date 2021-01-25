package com.wundermanthompson.markuply.javascript;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wundermanthompson.markuply.javascript.context.ContextExecutor;
import com.wundermanthompson.markuply.javascript.context.ContextPool;
import com.wundermanthompson.markuply.javascript.context.provider.BaseContextProvider;
import com.wundermanthompson.markuply.javascript.context.provider.CachedContextProvider;
import com.wundermanthompson.markuply.javascript.context.provider.ContextProvider;
import com.wundermanthompson.markuply.javascript.repository.CachedScriptProvider;
import com.wundermanthompson.markuply.javascript.repository.ClasspathScriptProvider;
import com.wundermanthompson.markuply.javascript.repository.ExternalScriptProvider;
import com.wundermanthompson.markuply.javascript.repository.ScriptProvider;

import java.time.Duration;
import java.util.function.Consumer;

public class JavascriptRendererConfigurator {

  private ObjectMapper objectMapper;
  private ScriptProvider scriptProvider;

  public static JavascriptRendererConfigurator instance() {
    return new JavascriptRendererConfigurator();
  }

  public JavascriptRendererConfigurator classpathScript(String resourcePath) {
    scriptProvider = ClasspathScriptProvider.of(resourcePath);
    return this;
  }

  public JavascriptRendererConfigurator externalScript(Consumer<ExternalScriptProvider.Builder> repositoryConfigurer) {
    ExternalScriptProvider.Builder builder = ExternalScriptProvider.builder();
    repositoryConfigurer.accept(builder);
    this.scriptProvider = builder.build();
    return this;
  }

  public JavascriptRendererConfigurator scriptProvider(ScriptProvider scriptProvider) {
    this.scriptProvider = scriptProvider;
    return this;
  }

  /**
   * By default the ObjectMapper registered in Spring context is used.
   *
   * @param objectMapper Jackson mapper to be used within renderer
   * @return builder
   */
  public JavascriptRendererConfigurator objectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    return this;
  }

  public JavascriptRenderer buildProduction() {
    validate();
    ScriptProvider cachedScriptProvider = CachedScriptProvider.of(scriptProvider);
    ContextProvider contextProvider = BaseContextProvider.of(cachedScriptProvider);
    ContextExecutor executor = ContextPool.production(contextProvider);
    return JavascriptRenderer.of(objectMapper, executor);
  }

  public JavascriptRenderer buildDevelopment() {
    validate();
    ScriptProvider cachedScriptProvider = CachedScriptProvider.of(scriptProvider, Duration.ofMillis(100));
    ContextProvider contextProvider = CachedContextProvider.of(cachedScriptProvider);
    ContextExecutor executor = ContextPool.development(contextProvider);
    return JavascriptRenderer.of(objectMapper, executor);
  }

  private void validate() {
    if (scriptProvider == null) {
      throw new IllegalStateException(
          "Bundle repository must be configured. Use classpath/external/bundleRepository methods.");
    }
  }

}
