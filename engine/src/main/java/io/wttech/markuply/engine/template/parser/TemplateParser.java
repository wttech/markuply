package io.wttech.markuply.engine.template.parser;

import io.wttech.markuply.engine.template.graph.node.FragmentGraph;

public interface TemplateParser {

  String COMPONENT_ATTRIBUTE = "data-markuply-component";
  String PROPS_ATTRIBUTE = "data-props";
  String SECTION_ATTRIBUTE = "data-markuply-section";

  FragmentGraph parse(String html);

}
