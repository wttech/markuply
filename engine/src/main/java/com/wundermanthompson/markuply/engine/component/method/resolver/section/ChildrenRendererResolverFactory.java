package com.wundermanthompson.markuply.engine.component.method.resolver.section;

import com.wundermanthompson.markuply.engine.component.MarkuplyComponentContext;
import com.wundermanthompson.markuply.engine.component.method.resolver.MethodArgumentResolver;
import com.wundermanthompson.markuply.engine.component.method.resolver.MethodArgumentResolverFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;
import java.util.Optional;

@Component
public class ChildrenRendererResolverFactory implements MethodArgumentResolverFactory {

  @Override
  public Optional<MethodArgumentResolver> createResolver(Parameter parameter) {
    if (parameter.getType().equals(ChildrenRenderer.class)) {
      return Optional.of(ChildrenRendererResolver.instance());
    } else {
      return Optional.empty();
    }
  }

  @AllArgsConstructor(staticName = "instance")
  private static class ChildrenRendererResolver implements MethodArgumentResolver {

    @Override
    public ChildrenRenderer resolve(MarkuplyComponentContext context) {
      return SectionRenderer.of(context.getSections(), context.getPageContext());
    }

  }
}
