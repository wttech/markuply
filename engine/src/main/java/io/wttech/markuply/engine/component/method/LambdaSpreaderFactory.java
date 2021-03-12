package io.wttech.markuply.engine.component.method;

import io.wttech.markuply.engine.component.method.spreader.ParameterSpreader;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import java.lang.invoke.*;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor(staticName = "of")
public class LambdaSpreaderFactory<T> {

  private static final Class<?> ERASED_TYPE = Object.class;

  private final Class<T> functionType;
  private final Function<T, ParameterSpreader> spreaderCreator;

  public ParameterSpreader<Mono<String>> buildRenderSpreader(Object instance, Method method) {
    if (!method.getReturnType().equals(Mono.class)) {
      throw new IllegalArgumentException("Method to augment must return Mono<String>");
    }
    try {
      Class<?>[] parameterTypes = method.getParameterTypes();
      // due to type erasure real signature must contain Object.class for all parameters
      List<Class<?>> erasedParameterTypeList =
          Stream.generate(() -> Object.class)
              .limit(parameterTypes.length)
              .collect(Collectors.toList());

      // turn parameter types array to typed list
      List<Class<?>> realParameterTypeList = new ArrayList<>();
      Collections.addAll(realParameterTypeList, parameterTypes);

      Class<?> returnType = method.getReturnType();

      Lookup lookup = MethodHandles.lookup();
      MethodHandle methodHandle = lookup.unreflect(method);

      CallSite callSite =
          LambdaMetafactory.metafactory(
              lookup,
              "apply",
              MethodType.methodType(functionType, method.getDeclaringClass()),
              // type erasure, Supplier will return an Object
              MethodType.methodType(ERASED_TYPE, erasedParameterTypeList),
              methodHandle,
              MethodType.methodType(returnType, realParameterTypeList));

      Object untypedLambda = callSite.getTarget().bindTo(instance).invoke();

      return spreaderCreator.apply((T) untypedLambda);
    } catch (Throwable e) {
      throw new IllegalArgumentException(e);
    }
  }

}
