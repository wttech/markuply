package com.wundermanthompson.markuply.engine.renderer;

import com.wundermanthompson.markuply.engine.template.graph.RenderFunction;
import com.wundermanthompson.markuply.engine.template.graph.RenderingVisitor;
import com.wundermanthompson.markuply.engine.template.graph.node.FragmentGraph;
import com.wundermanthompson.markuply.engine.template.parser.TemplateParser;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

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
