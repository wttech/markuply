package io.wttech.markuply.engine.component.method;

import io.wttech.markuply.engine.component.MarkuplyComponent;
import io.wttech.markuply.engine.component.MarkuplyComponentContext;
import io.wttech.markuply.engine.component.method.resolver.MethodArgumentResolver;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.List;

@RequiredArgsConstructor(staticName = "of")
public class ReflectiveMethodComponent implements MarkuplyComponent {

  private final Object targetInstance;
  private final Method method;
  private final List<MethodArgumentResolver> resolvers;

  @Override
  public Mono<String> render(MarkuplyComponentContext context) {
    try {
      Object[] parameters =
          resolvers.stream()
              .map(resolver -> resolver.resolve(context))
              .toArray();
      Object result = method.invoke(targetInstance, parameters);
      return (Mono<String>) result;
    } catch (Throwable throwable) {
      return Mono.error(throwable);
    }
  }

}
