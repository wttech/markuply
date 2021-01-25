package com.wundermanthompson.markuply.engine.template.graph.node;

import com.wundermanthompson.markuply.engine.template.graph.FragmentGraphVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@Getter
public class RootFragment implements FragmentGraph {

  private final List<FragmentGraph> children;

  @Override
  public <T> T accept(FragmentGraphVisitor<T> visitor) {
    return visitor.visit(children);
  }

}
