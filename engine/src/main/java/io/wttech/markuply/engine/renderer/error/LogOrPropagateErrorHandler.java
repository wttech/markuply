package io.wttech.markuply.engine.renderer.error;

import io.wttech.markuply.engine.pipeline.context.PageContext;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class LogOrPropagateErrorHandler implements ComponentErrorHandler {

  @NonNull
  private final List<Class<? extends Throwable>> propagate;

  @Override
  public Mono<String> handleError(Throwable throwable, String componentId, String props,
                                  PageContext context) {
    boolean typeToPropagate = propagate.stream().anyMatch(type -> type.isAssignableFrom(throwable.getClass()));
    if (typeToPropagate) {
      return Mono.error(throwable);
    } else {
      log.error("Error occurred during component rendering. Component: {}, props: {}", componentId, props, throwable);
      return Mono.just("");
    }
  }

}
