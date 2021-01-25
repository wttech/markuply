package com.wundermanthompson.markuply.examples.helloworldjs.hello;

import com.wundermanthompson.markuply.engine.component.Markuply;
import com.wundermanthompson.markuply.engine.component.method.resolver.properties.Props;
import com.wundermanthompson.markuply.javascript.JavascriptRenderer;
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
