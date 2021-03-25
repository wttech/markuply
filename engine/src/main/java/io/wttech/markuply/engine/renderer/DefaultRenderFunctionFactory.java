package io.wttech.markuply.engine.renderer;

import io.wttech.markuply.engine.template.graph.RenderFunction;
import io.wttech.markuply.engine.template.graph.RenderingVisitor;
import io.wttech.markuply.engine.template.graph.node.FragmentGraph;
import io.wttech.markuply.engine.template.parser.TemplateParser;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@AllArgsConstructor(staticName = "instance")
@Slf4j
public class DefaultRenderFunctionFactory implements RenderFunctionFactory {

  @NonNull
  private final TemplateParser templateParser;
  @NonNull
  private final ComponentRenderer componentRenderer;

  @Override
  public RenderFunction create(String html) {
    FragmentGraph fragmentGraph = templateParser.parse(html);
    RenderingVisitor visitor = RenderingVisitor.of(componentRenderer);
    return fragmentGraph.accept(visitor);
  }

}
