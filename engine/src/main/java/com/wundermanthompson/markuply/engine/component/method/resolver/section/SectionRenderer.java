package com.wundermanthompson.markuply.engine.component.method.resolver.section;

import com.wundermanthompson.markuply.engine.MarkuplyException;
import com.wundermanthompson.markuply.engine.pipeline.context.PageContext;
import com.wundermanthompson.markuply.engine.template.graph.NamedRenderFunctions;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor(staticName = "of")
public class SectionRenderer implements ChildrenRenderer {

  private final NamedRenderFunctions sections;
  private final PageContext originalContext;

  public Mono<String> render() {
    return render(originalContext);
  }

  public Mono<String> render(String name) {
    return render(name, originalContext);
  }

  public Mono<String> render(PageContext context) {
    return sections.findDefaultSection()
        .orElseThrow(() -> new MarkuplyException("There is no default section to render. All are named."))
        .render(context);
  }

  public Mono<String> render(String name, PageContext context) {
    return sections.findSection(name)
        .orElseThrow(() -> new MarkuplyException("There is no section named " + name + " to render."))
        .render(context);
  }

}
