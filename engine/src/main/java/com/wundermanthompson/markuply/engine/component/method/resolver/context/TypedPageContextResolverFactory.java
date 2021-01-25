package com.wundermanthompson.markuply.engine.component.method.resolver.context;

import com.wundermanthompson.markuply.engine.component.MarkuplyComponentContext;
import com.wundermanthompson.markuply.engine.component.method.resolver.MethodArgumentResolver;
import com.wundermanthompson.markuply.engine.component.method.resolver.MethodArgumentResolverFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;
import java.util.Optional;

@AllArgsConstructor
@Component
public class TypedPageContextResolverFactory implements MethodArgumentResolverFactory {

  @Override
  public Optional<MethodArgumentResolver> createResolver(Parameter parameter) {
    if (parameter.isAnnotationPresent(Context.class)) {
      return Optional.of(TypedPageContextResolver.of(parameter.getType()));
    } else {
      return Optional.empty();
    }
  }

  @AllArgsConstructor(staticName = "of")
  private static class TypedPageContextResolver implements MethodArgumentResolver {

    private final Class<?> contextKey;

    @Override
    public Object resolve(MarkuplyComponentContext context) {
      return context.getPageContext().get(contextKey);
    }

  }
}
