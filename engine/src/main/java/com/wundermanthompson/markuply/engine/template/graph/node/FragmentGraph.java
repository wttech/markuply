package com.wundermanthompson.markuply.engine.template.graph.node;

import com.wundermanthompson.markuply.engine.template.graph.FragmentGraphVisitor;

public interface FragmentGraph {

  <T> T accept(FragmentGraphVisitor<T> visitor);

}
