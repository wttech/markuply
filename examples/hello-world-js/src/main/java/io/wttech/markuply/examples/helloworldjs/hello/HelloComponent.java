package io.wttech.markuply.examples.helloworldjs.hello;

import io.wttech.graal.templating.javascript.JavascriptRenderer;
import io.wttech.markuply.engine.component.Markuply;
import io.wttech.markuply.engine.component.method.resolver.properties.Props;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class HelloComponent {

  private static final String JS_VIEW_NAME = "hello";

  private final JavascriptRenderer renderer;

  public HelloComponent(JavascriptRenderer renderer) {
    this.renderer = renderer;
  }

  @Markuply("hello")
  public Mono<String> render(@Props String name) {
    return renderer.render(JS_VIEW_NAME, String.format("{\"name\":\"%s\"}", name));
  }

}
