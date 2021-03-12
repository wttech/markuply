package io.wttech.markuply.engine.template.parser.jsoup;

import io.wttech.markuply.engine.template.parser.TemplateParser;
import io.wttech.markuply.engine.template.parser.TemplateParserException;
import io.wttech.markuply.engine.template.graph.node.*;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Chops a page into static and dynamic component parts.
 */
@AllArgsConstructor(staticName = "instance")
public class JsoupTemplateParser implements TemplateParser {

  private static final String PLACEHOLDER_COMMENT = "___data-markuply-parser-placeholder___";
  private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("<!--" + PLACEHOLDER_COMMENT + "-->");

  private static final String MARKER_ATTRIBUTE = "data-markuply-component";
  private static final String ATTRIBUTE_SELECTOR = "[" + MARKER_ATTRIBUTE + "]";

  private static final String PROPS_ATTRIBUTE = "data-props";
  private static final String SECTION_ATTRIBUTE = "data-markuply-section";

  @Override
  public FragmentGraph parse(String html) {
    Document document = Jsoup.parse(html);
    List<FragmentGraph> children = parseChildren(document);
    return RootFragment.of(children);
  }

  private List<FragmentGraph> parseChildren(Element rootNode) {
    List<ComponentFragment> componentFragments = extractComponents(rootNode);

    String htmlWithPlaceholders = rootNode.html();

    List<FragmentGraph> children = new ArrayList<>();
    if (componentFragments.isEmpty()) {
      children.add(StaticFragment.of(htmlWithPlaceholders));
    } else {
      Matcher matcher = PLACEHOLDER_PATTERN.matcher(htmlWithPlaceholders);

      int lastMatchEnd = 0;

      int numberOfMatches = 0;

      while (matcher.find()) {
        int start = matcher.start();
        if (start > lastMatchEnd) {
          String staticPart = htmlWithPlaceholders.substring(lastMatchEnd, start);
          children.add(StaticFragment.of(staticPart));
        }
        children.add(componentFragments.get(numberOfMatches));
        lastMatchEnd = matcher.end();
        numberOfMatches++;
      }

      if (lastMatchEnd < htmlWithPlaceholders.length()) {
        children.add(StaticFragment.of(htmlWithPlaceholders.substring(lastMatchEnd)));
      }

    }
    return children;
  }

  private Element findNextMarkuplyElement(Element root) {
    return root.selectFirst(ATTRIBUTE_SELECTOR);
  }

  private List<ComponentFragment> extractComponents(Element root) {
    List<ComponentFragment> componentFragments = new ArrayList<>();
    Element templatingElement = findNextMarkuplyElement(root);
    while (templatingElement != null) {
      componentFragments.add(htmlToComponentFragment(templatingElement));
      replaceWithPlaceholder(templatingElement);
      templatingElement = findNextMarkuplyElement(root);
    }
    return componentFragments;
  }

  private ComponentFragment htmlToComponentFragment(Element templatingElement) {
    String props = templatingElement.attr(PROPS_ATTRIBUTE);
    String componentId = templatingElement.attr(MARKER_ATTRIBUTE);
    templatingElement.removeAttr(MARKER_ATTRIBUTE);
    templatingElement.removeAttr(PROPS_ATTRIBUTE);
    Elements children = templatingElement.children();
    boolean unwrapedSingleSection = children.stream().noneMatch(element -> element.attributes().hasKey(SECTION_ATTRIBUTE));
    boolean allNamedSections = children.stream().allMatch(element -> element.attributes().hasKey(SECTION_ATTRIBUTE));
    List<ComponentSectionFragment> sections;
    if (unwrapedSingleSection) {
      sections = Collections.singletonList(ComponentSectionFragment.instance(ComponentSectionFragment.DEFAULT_SECTION_NAME, parseChildren(templatingElement)));
    } else if (allNamedSections) {
      sections = children.stream()
          .map(element -> ComponentSectionFragment.instance(element.attr(SECTION_ATTRIBUTE), parseChildren(element)))
          .collect(Collectors.toList());
    } else {
      throw new TemplateParserException("Either all direct child elements of data-markuply-component element must be tagged with data-markuply-section or none of them");
    }
    return ComponentFragment
        .of(componentId, props, sections);
  }

  private void replaceWithPlaceholder(Element templatingElement) {
    templatingElement.replaceWith(new Comment(PLACEHOLDER_COMMENT));
  }

}
