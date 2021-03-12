package io.wttech.markuply.engine.template.graph.node;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(staticName = "instance")
@Getter
public class ComponentSectionFragment {

  public static final String DEFAULT_SECTION_NAME = "";

  private final String name;
  private final List<FragmentGraph> children;

  public static ComponentSectionFragment instance(List<FragmentGraph> fragments) {
    return new ComponentSectionFragment(DEFAULT_SECTION_NAME, fragments);
  }

}
