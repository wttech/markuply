package io.wttech.markuply.engine.template.graph.node;

import io.wttech.markuply.engine.template.graph.FragmentGraphVisitor;

public interface FragmentGraph {

  <T> T accept(FragmentGraphVisitor<T> visitor);

}
