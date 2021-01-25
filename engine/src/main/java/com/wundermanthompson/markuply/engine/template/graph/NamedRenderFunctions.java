package com.wundermanthompson.markuply.engine.template.graph;

import com.wundermanthompson.markuply.engine.MarkuplyException;
import com.wundermanthompson.markuply.engine.template.graph.node.ComponentSectionFragment;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class NamedRenderFunctions {

  public static final NamedRenderFunctions NO_OP = builder().addSection(RenderFunction.NO_OP).build();

  private final Map<String, RenderFunction> sections;

  public Optional<RenderFunction> findSection(String name) {
    return Optional.ofNullable(sections.get(name));
  }

  public Optional<RenderFunction> findDefaultSection() {
    return Optional.ofNullable(sections.get(ComponentSectionFragment.DEFAULT_SECTION_NAME));
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private final Map<String, RenderFunction> sections = new HashMap<>();

    public Builder addSection(RenderFunction renderFunction) {
      return addSection(ComponentSectionFragment.DEFAULT_SECTION_NAME, renderFunction);
    }

    public Builder addSection(String name, RenderFunction renderFunction) {
      if (sections.containsKey(name)) {
        throw new MarkuplyException("Cannot add another section with the same name");
      }
      sections.put(name, renderFunction);
      return this;
    }

    public NamedRenderFunctions build() {
      return new NamedRenderFunctions(sections);
    }

  }

}
