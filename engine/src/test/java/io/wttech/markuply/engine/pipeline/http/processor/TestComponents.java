package io.wttech.markuply.engine.pipeline.http.processor;

import io.wttech.markuply.engine.component.Markuply;
import io.wttech.markuply.engine.component.method.resolver.properties.Props;
import io.wttech.markuply.engine.component.method.resolver.section.ChildrenRenderer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class TestComponents {

  @Markuply
  public Mono<String> hello(@Props String props) {
    return Mono.just("<div>" + props + "</div>");
  }

  @Markuply
  public Mono<String> error() {
    throw new RuntimeException("Error");
  }

  @Markuply
  public Mono<String> metadata() {
    return Mono.just("<title>Test</title>");
  }

  @Markuply
  public Mono<String> renderNamedSection(ChildrenRenderer renderer) {
    return renderer.render("namedSection");
  }

  @Markuply
  public Mono<String> renderDefaultSection(ChildrenRenderer renderer) {
    return renderer.render().map(String::trim);
  }

}
