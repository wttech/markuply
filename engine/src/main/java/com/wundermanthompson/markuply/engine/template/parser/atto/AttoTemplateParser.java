package com.wundermanthompson.markuply.engine.template.parser.atto;

import com.wundermanthompson.markuply.engine.template.graph.node.FragmentGraph;
import com.wundermanthompson.markuply.engine.template.parser.TemplateParser;
import com.wundermanthompson.markuply.engine.template.parser.TemplateParserException;
import lombok.RequiredArgsConstructor;
import org.attoparser.IMarkupParser;
import org.attoparser.MarkupParser;
import org.attoparser.ParseException;
import org.attoparser.config.ParseConfiguration;

@RequiredArgsConstructor(staticName = "instance")
public class AttoTemplateParser implements TemplateParser {

  private final IMarkupParser parser = new MarkupParser(ParseConfiguration.htmlConfiguration());

  @Override
  public FragmentGraph parse(String html) {
    try {
      StackHandler handler = StackHandler.instance();
      parser.parse(html, handler);
      return handler.getResult();
    } catch (ParseException e) {
      throw new TemplateParserException("Could not parse the template", e);
    }
  }

}
