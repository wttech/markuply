package com.wundermanthompson.markuply.engine.template.graph;

import com.wundermanthompson.markuply.engine.template.graph.node.ComponentFragment;
import com.wundermanthompson.markuply.engine.template.graph.node.FragmentGraph;
import com.wundermanthompson.markuply.engine.template.graph.node.StaticFragment;

import java.util.List;

public interface FragmentGraphVisitor<T> {

  T visit(StaticFragment staticFragment);

  T visit(ComponentFragment componentFragment);

  T visit(List<FragmentGraph> children);

}
