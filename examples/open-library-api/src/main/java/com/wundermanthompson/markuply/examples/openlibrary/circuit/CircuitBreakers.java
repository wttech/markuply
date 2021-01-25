package com.wundermanthompson.markuply.examples.openlibrary.circuit;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
@AllArgsConstructor
public class CircuitBreakers {

  private final CircuitBreakerRegistry registry;

  public <T> Mono<T> circuitBreak(Mono<T> original) {
    CircuitBreaker base = registry.circuitBreaker("base");
    return original
        .transform(CircuitBreakerOperator.of(base));
  }

  public <T> Mono<T> circuitBreak(Mono<T> original, Function<CallNotPermittedException, Mono<T>> fallback) {
    CircuitBreaker base = registry.circuitBreaker("base");
    return original
        .transform(CircuitBreakerOperator.of(base))
        .onErrorResume(CallNotPermittedException.class, fallback);
  }


}
