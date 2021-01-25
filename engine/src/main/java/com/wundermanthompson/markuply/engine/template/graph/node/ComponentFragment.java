package com.wundermanthompson.markuply.engine.template.graph.node;

import com.wundermanthompson.markuply.engine.template.graph.FragmentGraphVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(staticName = "of")
@Getter
public class ComponentFragment implements FragmentGraph {

  private final String componentId;
  private final String props;
  private final List<ComponentSectionFragment> sections;

  public static ComponentFragment simple(String componentId, String props) {
    return new ComponentFragment(componentId, props, new ArrayList<>());
  }

  @Override
  public <T> T accept(FragmentGraphVisitor<T> visitor) {
    return visitor.visit(this);
  }

}
