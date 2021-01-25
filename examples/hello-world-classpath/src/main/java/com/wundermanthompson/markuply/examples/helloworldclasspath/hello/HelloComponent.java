package com.wundermanthompson.markuply.examples.helloworldclasspath.hello;

import com.wundermanthompson.markuply.engine.component.Markuply;
import com.wundermanthompson.markuply.engine.component.method.resolver.properties.Props;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class HelloComponent {

  @Markuply("hello")
  public Mono<String> renderHelloComponent(@Props String props) {
    String name = props == null || props.isEmpty()
        ? "World"
        : props;
    return Mono.just(String.format("<div>Hello %s!</div>", name));
  }

}
