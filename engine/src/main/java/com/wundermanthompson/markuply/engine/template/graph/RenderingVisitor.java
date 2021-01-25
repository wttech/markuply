package com.wundermanthompson.markuply.engine.template.graph;

import com.wundermanthompson.markuply.engine.renderer.ComponentRenderer;
import com.wundermanthompson.markuply.engine.template.graph.node.ComponentFragment;
import com.wundermanthompson.markuply.engine.template.graph.node.ComponentSectionFragment;
import com.wundermanthompson.markuply.engine.template.graph.node.FragmentGraph;
import com.wundermanthompson.markuply.engine.template.graph.node.StaticFragment;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(staticName = "of")
public class RenderingVisitor implements FragmentGraphVisitor<RenderFunction> {

  private final ComponentRenderer componentRenderer;

  @Override
  public RenderFunction visit(ComponentFragment componentFragment) {
    NamedRenderFunctions sectionFunctions = handleSections(componentFragment.getSections());
    return context -> componentRenderer.render(componentFragment.getComponentId(), componentFragment.getProps(), context, sectionFunctions);
  }

  private NamedRenderFunctions handleSections(List<ComponentSectionFragment> sections) {
    NamedRenderFunctions.Builder builder = NamedRenderFunctions.builder();
    sections.forEach(section -> builder.addSection(section.getName(), handleChildren(section.getChildren())));
    return builder.build();
  }

  private RenderFunction handleChildren(List<FragmentGraph> children) {
    List<RenderFunction> innerFunctions = children.stream().map(fragment -> fragment.accept(this)).collect(Collectors.toList());
    return context -> {
      return Flux.fromIterable(innerFunctions)
          .flatMapSequential(childRenderer -> childRenderer.render(context))
          .collect(Collectors.joining());
    };
  }

  @Override
  public RenderFunction visit(StaticFragment staticFragment) {
    return context -> Mono.just(staticFragment.getContent());
  }

  @Override
  public RenderFunction visit(List<FragmentGraph> children) {
    return handleChildren(children);
  }

}
