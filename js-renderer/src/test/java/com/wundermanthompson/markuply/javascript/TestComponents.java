package com.wundermanthompson.markuply.javascript;

import com.wundermanthompson.markuply.engine.component.Markuply;
import com.wundermanthompson.markuply.engine.component.method.resolver.properties.Props;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class TestComponents {

  private final JavascriptRenderer renderer;

  @Markuply
  public Mono<String> hello(@Props String props) {
    return renderer.render("hello", props);
  }

}
