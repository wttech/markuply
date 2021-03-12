package io.wttech.markuply.engine.template.graph;

import io.wttech.markuply.engine.template.graph.node.ComponentFragment;
import io.wttech.markuply.engine.template.graph.node.FragmentGraph;
import io.wttech.markuply.engine.template.graph.node.StaticFragment;

import java.util.List;

public interface FragmentGraphVisitor<T> {

  T visit(StaticFragment staticFragment);

  T visit(ComponentFragment componentFragment);

  T visit(List<FragmentGraph> children);

}
