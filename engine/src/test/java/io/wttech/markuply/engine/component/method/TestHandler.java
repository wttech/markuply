package io.wttech.markuply.engine.component.method;

import io.wttech.markuply.engine.component.method.resolver.context.Context;
import io.wttech.markuply.engine.component.method.resolver.properties.Props;
import io.wttech.markuply.engine.component.method.resolver.section.ChildrenRenderer;
import io.wttech.markuply.engine.pipeline.context.PageContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class TestHandler {

  public Mono<String> rawProps(@Props String props) {
    return Mono.just("<div>" + props + "</div>");
  }

  public String nonReactiveRawProps(@Props String props) {
    return "<div>" + props + "</div>";
  }

  public int incorrectReturnType(@Props String props) {
    return 2;
  }

  public Mono<String> typedProps(@Props SimpleProps props) {
    return Mono.just("<div>" + props.getName() + "</div>");
  }

  public Mono<String> rawContext(PageContext context) {
    boolean contextPresent = context != null;
    return Mono.just("<div>" + contextPresent + "</div>");
  }

  public Mono<String> typedContext(@Context String stringContext) {
    return Mono.just("<div>" + stringContext + "</div>");
  }

  public Mono<String> childrenRenderer(ChildrenRenderer childrenRenderer) {
    boolean rendererPresent = childrenRenderer != null;
    return Mono.just("<div>" + rendererPresent + "</div>");
  }

  public Mono<String> fullSet(@Props SimpleProps props, @Context Integer context) {
    boolean contextPresent = context != null;
    boolean propsPresent = props != null;
    boolean allPresent = contextPresent && propsPresent;
    return Mono.just("<div>" + allPresent + "</div>");
  }

  public Mono<String> fullSetReordered(@Props SimpleProps props, ChildrenRenderer childrenRenderer) {
    boolean propsPresent = props != null;
    boolean rendererPresent = childrenRenderer != null;
    boolean allPresent = propsPresent && rendererPresent;
    return Mono.just("<div>" + allPresent + "</div>");
  }

}
