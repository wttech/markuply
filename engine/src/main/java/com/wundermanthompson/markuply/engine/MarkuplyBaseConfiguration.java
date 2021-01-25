package com.wundermanthompson.markuply.engine;

import com.wundermanthompson.markuply.engine.component.method.LambdaComponentFactory;
import com.wundermanthompson.markuply.engine.component.method.resolver.context.PageContextResolverFactory;
import com.wundermanthompson.markuply.engine.component.method.resolver.context.TypedPageContextResolverFactory;
import com.wundermanthompson.markuply.engine.component.method.resolver.properties.PropsResolverFactory;
import com.wundermanthompson.markuply.engine.component.method.resolver.properties.TypedPropsResolverFactory;
import com.wundermanthompson.markuply.engine.component.method.resolver.section.ChildrenRendererResolverFactory;
import com.wundermanthompson.markuply.engine.pipeline.http.proxy.configuration.HeaderPropertiesParser;
import com.wundermanthompson.markuply.engine.pipeline.http.proxy.request.configuration.PageRequestConfigurableEnricherFactory;
import com.wundermanthompson.markuply.engine.pipeline.http.proxy.request.configuration.RequestEnricherSpringProperties;
import com.wundermanthompson.markuply.engine.pipeline.http.proxy.response.PageResponseConfigurableEnricherFactory;
import com.wundermanthompson.markuply.engine.pipeline.http.proxy.response.configuration.ResponseEnricherSpringProperties;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.configuration.HttpPageRepositorySpringConfigurator;
import com.wundermanthompson.markuply.engine.pipeline.http.repository.configuration.HttpRepositorySpringProperties;
import com.wundermanthompson.markuply.engine.renderer.ComponentRenderer;
import com.wundermanthompson.markuply.engine.renderer.cache.RenderFunctionCacheProperties;
import com.wundermanthompson.markuply.engine.renderer.spring.MarkuplyBeanPostProcessor;
import com.wundermanthompson.markuply.engine.request.cache.RequestCacheAspect;
import com.wundermanthompson.markuply.engine.request.cache.RequestCacheFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RequestCacheAspect.class, RequestCacheFilter.class, ComponentRenderer.class,
    MarkuplyBeanPostProcessor.class, LambdaComponentFactory.class, PageContextResolverFactory.class,
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
