package io.wttech.markuply.engine.component.method;

import io.wttech.markuply.engine.component.MarkuplyComponent;
import io.wttech.markuply.engine.component.MarkuplyComponentContext;
import io.wttech.markuply.engine.component.method.resolver.MethodArgumentResolver;
import io.wttech.markuply.engine.component.method.spreader.ParameterSpreader;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Markuply component using LambdaMetafactory to invoke annotated methods
 */
@AllArgsConstructor(staticName = "of")
public class MethodComponent implements MarkuplyComponent {

  private final ParameterSpreader<Mono<String>> spreader;
  private final List<MethodArgumentResolver> resolvers;

  @Override
  public Mono<String> render(MarkuplyComponentContext context) {
    try {
      List<Object> parameters =
          resolvers.stream()
              .map(resolver -> resolver.resolve(context))
              .collect(Collectors.toList());
      return spreader.invoke(parameters);
    } catch (Throwable throwable) {
      return Mono.error(throwable);
    }
  }

}
