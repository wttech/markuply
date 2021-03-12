package io.wttech.markuply.engine;

import io.wttech.markuply.engine.component.method.LambdaComponentFactory;
import io.wttech.markuply.engine.component.method.resolver.context.PageContextResolverFactory;
import io.wttech.markuply.engine.component.method.resolver.context.TypedPageContextResolverFactory;
import io.wttech.markuply.engine.component.method.resolver.properties.PropsResolverFactory;
import io.wttech.markuply.engine.component.method.resolver.properties.TypedPropsResolverFactory;
import io.wttech.markuply.engine.component.method.resolver.section.ChildrenRendererResolverFactory;
import io.wttech.markuply.engine.pipeline.http.proxy.configuration.HeaderPropertiesParser;
import io.wttech.markuply.engine.pipeline.http.proxy.request.configuration.PageRequestConfigurableEnricherFactory;
import io.wttech.markuply.engine.pipeline.http.proxy.request.configuration.RequestEnricherSpringProperties;
import io.wttech.markuply.engine.pipeline.http.proxy.response.PageResponseConfigurableEnricherFactory;
import io.wttech.markuply.engine.pipeline.http.proxy.response.configuration.ResponseEnricherSpringProperties;
import io.wttech.markuply.engine.pipeline.http.repository.configuration.HttpPageRepositorySpringConfigurator;
import io.wttech.markuply.engine.pipeline.http.repository.configuration.HttpRepositorySpringProperties;
import io.wttech.markuply.engine.renderer.ComponentRenderer;
import io.wttech.markuply.engine.renderer.cache.RenderFunctionCacheProperties;
import io.wttech.markuply.engine.request.cache.RequestCacheAspect;
import io.wttech.markuply.engine.request.cache.RequestCacheFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RequestCacheAspect.class, RequestCacheFilter.class, ComponentRenderer.class,
    LambdaComponentFactory.class, PageContextResolverFactory.class,
    PropsResolverFactory.class, TypedPropsResolverFactory.class, TypedPageContextResolverFactory.class,
    ChildrenRendererResolverFactory.class,
    HttpPageRepositorySpringConfigurator.class,
    PageResponseConfigurableEnricherFactory.class, PageRequestConfigurableEnricherFactory.class,
    HeaderPropertiesParser.class})
@EnableConfigurationProperties({
    ResponseEnricherSpringProperties.class, RequestEnricherSpringProperties.class,
    HttpRepositorySpringProperties.class, RenderFunctionCacheProperties.class
})
public class MarkuplyBaseConfiguration {
}
