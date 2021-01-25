package com.wundermanthompson.markuply.engine.template.parser.atto;

import org.attoparser.IMarkupHandler;

public interface FragmentHandler extends IMarkupHandler {

  void handleInnerResult(ComponentHandler handler);

  void handleInnerResult(ComponentSectionHandler handler);

  void reportEndToParent(FragmentHandler handler);

}
