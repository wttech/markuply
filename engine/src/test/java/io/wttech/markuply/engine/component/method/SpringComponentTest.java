package io.wttech.markuply.engine.component.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wttech.markuply.engine.component.MarkuplyComponentContext;
import io.wttech.markuply.engine.component.method.resolver.MethodArgumentResolverFactory;
import io.wttech.markuply.engine.component.method.resolver.context.PageContextResolverFactory;
import io.wttech.markuply.engine.component.method.resolver.context.TypedPageContextResolverFactory;
import io.wttech.markuply.engine.component.method.resolver.properties.PropsResolverFactory;
import io.wttech.markuply.engine.component.method.resolver.properties.TypedPropsResolverFactory;
import io.wttech.markuply.engine.component.method.resolver.section.ChildrenRenderer;
import io.wttech.markuply.engine.component.method.resolver.section.ChildrenRendererResolverFactory;
import io.wttech.markuply.engine.pipeline.context.PageContext;
import io.wttech.markuply.engine.template.graph.NamedRenderFunctions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = TestHandler.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class SpringComponentTest {

  @Inject
  TestHandler testHandler;

  private static final String SIMPLE_PROPS_JSON = "{\"name\": \"World\"}";

  @Test
  void rawProps() throws NoSuchMethodException {
    MethodComponent methodComponent = getTestMethod("rawProps", String.class);
    Mono<String> result = methodComponent
        .render(MarkuplyComponentContext.of("Hello World", PageContext.empty(), NamedRenderFunctions.NO_OP));
    StepVerifier.create(result).expectNext("<div>Hello World</div>").verifyComplete();
  }

  @Test
  void typedProps() throws NoSuchMethodException {
    MethodComponent methodComponent = getTestMethod("typedProps", SimpleProps.class);
    Mono<String> result = methodComponent
        .render(MarkuplyComponentContext.of(SIMPLE_PROPS_JSON, PageContext.empty(), NamedRenderFunctions.NO_OP));
    StepVerifier.create(result).expectNext("<div>World</div>").verifyComplete();
  }

  @Test
  void rawContext() throws NoSuchMethodException {
    MethodComponent methodComponent = getTestMethod("rawContext", PageContext.class);
    Mono<String> result = methodComponent
        .render(MarkuplyComponentContext.of("Hello World", PageContext.empty(), NamedRenderFunctions.NO_OP));
    StepVerifier.create(result).expectNext("<div>true</div>").verifyComplete();
  }

  @Test
  void typedContext() throws NoSuchMethodException {
    MethodComponent methodComponent = getTestMethod("typedContext", String.class);
    Mono<String> result = methodComponent
        .render(MarkuplyComponentContext.of("", PageContext.of("Hello World"), NamedRenderFunctions.NO_OP));
    StepVerifier.create(result).expectNext("<div>Hello World</div>").verifyComplete();
  }

  @Test
  void fullSet() throws NoSuchMethodException {
    MethodComponent methodComponent = getTestMethod("fullSet", SimpleProps.class, Integer.class);
    Mono<String> result = methodComponent
        .render(MarkuplyComponentContext.of(SIMPLE_PROPS_JSON, PageContext.of(Integer.class, 5), NamedRenderFunctions.NO_OP));
    StepVerifier.create(result).expectNext("<div>true</div>").verifyComplete();
  }

  @Test
  void fullSetReordered() throws NoSuchMethodException {
    MethodComponent methodComponent = getTestMethod("fullSetReordered", SimpleProps.class, ChildrenRenderer.class);
    Mono<String> result = methodComponent
        .render(MarkuplyComponentContext.of(SIMPLE_PROPS_JSON, PageContext.of(Integer.class, 5), NamedRenderFunctions.NO_OP));
    StepVerifier.create(result).expectNext("<div>true</div>").verifyComplete();
  }

  private MethodComponent getTestMethod(String name, Class<?>... argumentTypes)
      throws NoSuchMethodException {
    List<MethodArgumentResolverFactory> resolverFactories = createResolverFactories();
    LambdaComponentFactory factory = new LambdaComponentFactory(resolverFactories);
    Method renderTestComponent = TestHandler.class.getMethod(name, argumentTypes);
    return factory.build(testHandler, renderTestComponent);
  }

  private List<MethodArgumentResolverFactory> createResolverFactories() {
    List<MethodArgumentResolverFactory> resolverFactories = new ArrayList<>();
    resolverFactories.add(new PropsResolverFactory());
    resolverFactories.add(new TypedPropsResolverFactory(new ObjectMapper()));
    resolverFactories.add(new PageContextResolverFactory());
    resolverFactories.add(new TypedPageContextResolverFactory());
    resolverFactories.add(new ChildrenRendererResolverFactory());
    return resolverFactories;
  }

}
