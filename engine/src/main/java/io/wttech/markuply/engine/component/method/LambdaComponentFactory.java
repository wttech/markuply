package io.wttech.markuply.engine.component.method;

import io.wttech.markuply.engine.component.ComponentDefinitionException;
import io.wttech.markuply.engine.component.MarkuplyComponent;
import io.wttech.markuply.engine.component.method.function.TriFunction;
import io.wttech.markuply.engine.component.method.function.UnifiedSupplier;
import io.wttech.markuply.engine.component.method.invoker.LambdaInvoker;
import io.wttech.markuply.engine.component.method.invoker.MethodInvoker;
import io.wttech.markuply.engine.component.method.invoker.ReflectiveInvoker;
import io.wttech.markuply.engine.component.method.resolver.MethodArgumentResolver;
import io.wttech.markuply.engine.component.method.resolver.MethodArgumentResolverFactory;
import io.wttech.markuply.engine.component.method.result.ResultConverter;
import io.wttech.markuply.engine.component.method.spreader.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class LambdaComponentFactory {

  private static final Map<Integer, LambdaSpreaderFactory<?>> SPREADER_FACTORIES = new HashMap<>();

  static {
    SPREADER_FACTORIES.put(0, LambdaSpreaderFactory.of(UnifiedSupplier.class,
        SupplierParameterSpreader::of));
    SPREADER_FACTORIES.put(1, LambdaSpreaderFactory.of(Function.class,
        FunctionParameterSpreader::of));
    SPREADER_FACTORIES.put(2, LambdaSpreaderFactory.of(BiFunction.class,
        BiFunctionParameterSpreader::of));
    SPREADER_FACTORIES.put(3, LambdaSpreaderFactory.of(TriFunction.class,
        TriFunctionParameterSpreader::of));
  }

  private final List<MethodArgumentResolverFactory> resolverFactories;

  public MarkuplyComponent build(Object instance, Method method) {
    checkArguments(method);
    checkReturnType(method);
    LambdaSpreaderFactory<?> lambdaSpreaderFactory = SPREADER_FACTORIES
        .get(method.getParameterCount());
    List<MethodArgumentResolver> resolvers = buildResolvers(method);
    if (method.getReturnType().equals(Mono.class)) {
      ParameterSpreader<Mono<String>> parameterSpreader = lambdaSpreaderFactory.buildRenderSpreader(instance, method);
      MethodInvoker<Mono<String>> invoker = LambdaInvoker.of(parameterSpreader);
      return MethodComponent.of(invoker, resolvers, ResultConverter.IDENTITY);
    } else {
      ParameterSpreader<String> parameterSpreader = lambdaSpreaderFactory.buildRenderSpreader(instance, method);
      MethodInvoker<String> invoker = LambdaInvoker.of(parameterSpreader);
      return MethodComponent.of(invoker, resolvers, Mono::just);
    }
  }

  public MarkuplyComponent buildReflective(Object instance, Method method) {
    checkArguments(method);
    checkReturnType(method);
    List<MethodArgumentResolver> resolvers = buildResolvers(method);
    if (method.getReturnType().equals(Mono.class)) {
      MethodInvoker<Mono<String>> invoker = ReflectiveInvoker.of(instance, method);
      return MethodComponent.of(invoker, resolvers, ResultConverter.IDENTITY);
    } else {
      MethodInvoker<String> invoker = ReflectiveInvoker.of(instance, method);
      return MethodComponent.of(invoker, resolvers, Mono::just);
    }
  }

  private void checkArguments(Method method) {
    if (method.getParameterCount() > 3) {
      throw new ComponentDefinitionException(
          "Method to augment cannot contain more than three arguments");
    }
  }

  private void checkReturnType(Method method) {
    if (!method.getReturnType().equals(Mono.class) && !method.getReturnType().equals(String.class)) {
      throw new ComponentDefinitionException(
          "Method to augment must return either Mono<String> or String");
    }
  }

  private List<MethodArgumentResolver> buildResolvers(Method method) {
    List<MethodArgumentResolver> resolvers = new ArrayList<>();
    Parameter[] parameters = method.getParameters();
    for (Parameter currentParameter : parameters) {
      MethodArgumentResolver resolver =
          resolverFactories.stream()
              .map(factory -> factory.createResolver(currentParameter))
              .filter(Optional::isPresent)
              .map(Optional::get)
              .findFirst()
              .orElseThrow(
                  () ->
                      new ComponentDefinitionException(
                          "Parameter resolver does not exist. Method: "
                              + method.getDeclaringClass().getName()
                              + "."
                              + method.getName()
                              + ". Parameter: "
                              + currentParameter.getName()));
      resolvers.add(resolver);
    }
    return resolvers;
  }
}
