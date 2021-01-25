package com.wundermanthompson.markuply.engine.template.graph.node;

import com.wundermanthompson.markuply.engine.template.graph.FragmentGraphVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class StaticFragment implements FragmentGraph {

  private final String content;

  @Override
  public <T> T accept(FragmentGraphVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
