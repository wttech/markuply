package com.wundermanthompson.markuply.engine.component.method.resolver.properties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wundermanthompson.markuply.engine.component.MarkuplyComponentContext;
import com.wundermanthompson.markuply.engine.component.method.resolver.ArgumentResolutionException;
import com.wundermanthompson.markuply.engine.component.method.resolver.MethodArgumentResolver;
import com.wundermanthompson.markuply.engine.component.method.resolver.MethodArgumentResolverFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;
import java.util.Optional;

@Component
@AllArgsConstructor
public class TypedPropsResolverFactory implements MethodArgumentResolverFactory {

  private final ObjectMapper objectMapper;

  @Override
  public Optional<MethodArgumentResolver> createResolver(Parameter parameter) {
    if (parameter.isAnnotationPresent(Props.class) && !parameter.getType().equals(String.class)) {
      return Optional.of(new TypedPropsResolver(objectMapper, parameter.getType()));
    } else {
      return Optional.empty();
    }
  }

  @AllArgsConstructor
  private static class TypedPropsResolver implements MethodArgumentResolver {

    private final ObjectMapper objectMapper;
    private final Class<?> targetType;

    @Override
    public Object resolve(MarkuplyComponentContext context) {
      try {
        String stringProps = context.getProps();
        return objectMapper.readValue(stringProps, targetType);
      } catch (JsonProcessingException e) {
        throw new ArgumentResolutionException("Cannot parse data-props as " + targetType.getName(), e);
      }
    }

  }

}
