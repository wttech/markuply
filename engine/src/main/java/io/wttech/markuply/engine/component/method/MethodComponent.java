package io.wttech.markuply.engine.component.method;

import io.wttech.markuply.engine.component.MarkuplyComponent;
import io.wttech.markuply.engine.component.MarkuplyComponentContext;
import io.wttech.markuply.engine.component.method.invoker.MethodInvoker;
import io.wttech.markuply.engine.component.method.resolver.MethodArgumentResolver;
import io.wttech.markuply.engine.component.method.result.ResultConverter;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Markuply component using LambdaMetafactory to invoke annotated methods
 */
@AllArgsConstructor(staticName = "of")
public class MethodComponent<T> implements MarkuplyComponent {

  private final MethodInvoker<T> invoker;
  private final List<MethodArgumentResolver> resolvers;
  private final ResultConverter<T> resultConverter;

  @Override
  public Mono<String> render(MarkuplyComponentContext context) {
    try {
      List<Object> parameters =
          resolvers.stream()
              .map(resolver -> resolver.resolve(context))
              .collect(Collectors.toList());
      T result = invoker.invoke(parameters);
      return resultConverter.convert(result);
    } catch (Throwable throwable) {
      return Mono.error(throwable);
    }
  }

}
