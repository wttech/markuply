package io.wttech.markuply.engine.component.method.result;

import reactor.core.publisher.Mono;

public interface ResultConverter<T> {

  ResultConverter<Mono<String>> IDENTITY = originalResult -> originalResult;

  Mono<String> convert(T originalResult);

}
