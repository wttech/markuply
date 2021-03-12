package io.wttech.markuply.engine.component.method.resolver.properties;

import io.wttech.markuply.engine.component.MarkuplyComponentContext;
import io.wttech.markuply.engine.component.method.resolver.MethodArgumentResolver;
import io.wttech.markuply.engine.component.method.resolver.MethodArgumentResolverFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;
import java.util.Optional;

@Component
public class PropsResolverFactory implements MethodArgumentResolverFactory {

  @Override
  public Optional<MethodArgumentResolver> createResolver(Parameter parameter) {
    if (parameter.isAnnotationPresent(Props.class) && parameter.getType().equals(String.class)) {
      return Optional.of(PropsResolver.instance());
    } else {
      return Optional.empty();
    }
  }

  @AllArgsConstructor(staticName = "instance")
  private static class PropsResolver implements MethodArgumentResolver {

    @Override
    public Object resolve(MarkuplyComponentContext context) {
      return context.getProps();
    }

  }

}
