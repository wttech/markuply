package com.wundermanthompson.markuply.engine.component.method.resolver.context;

import com.wundermanthompson.markuply.engine.component.MarkuplyComponentContext;
import com.wundermanthompson.markuply.engine.component.method.resolver.MethodArgumentResolver;
import com.wundermanthompson.markuply.engine.component.method.resolver.MethodArgumentResolverFactory;
import com.wundermanthompson.markuply.engine.pipeline.context.PageContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;
import java.util.Optional;

@Component
public class PageContextResolverFactory implements MethodArgumentResolverFactory {

  @Override
  public Optional<MethodArgumentResolver> createResolver(Parameter parameter) {
    if (parameter.getType().equals(PageContext.class)) {
      return Optional.of(PageContextResolver.instance());
    } else {
      return Optional.empty();
    }
  }

  @AllArgsConstructor(staticName = "instance")
  private static class PageContextResolver implements MethodArgumentResolver {

    @Override
    public Object resolve(MarkuplyComponentContext context) {
      return context.getPageContext();
    }

  }

}
