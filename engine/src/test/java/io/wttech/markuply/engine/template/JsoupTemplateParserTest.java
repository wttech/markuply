package io.wttech.markuply.engine.template;

import io.wttech.markuply.engine.template.graph.node.*;
import io.wttech.markuply.engine.template.parser.jsoup.JsoupTemplateParser;
import io.wttech.markuply.engine.utils.ClasspathFileLoader;
import org.junit.jupiter.api.Test;

import static io.wttech.markuply.engine.template.graph.node.ComponentSectionFragment.DEFAULT_SECTION_NAME;
import static org.assertj.core.api.Assertions.assertThat;

public class JsoupTemplateParserTest {

  private final ClasspathFileLoader loader = ClasspathFileLoader.instance();

  @Test
  void fullStaticPage() {
    JsoupTemplateParser parser = JsoupTemplateParser.instance();
    String content = loader.loadBlocking("/pages/fullStaticPage.html");
    FragmentGraph graph = parser.parse(content);

    RootFragment root = assertRoot(graph, 1);
    assertStaticFragment(root.getChildren().get(0));
  }

  @Test
  void singleComponentPage() {
    JsoupTemplateParser parser = JsoupTemplateParser.instance();
    String content = loader.loadBlocking("/pages/singleComponentPage.html");
    FragmentGraph graph = parser.parse(content);

    RootFragment root = assertRoot(graph, 3);
    assertStaticFragment(root.getChildren().get(0));
    assertComponent(root.getChildren().get(1), "hello", "World");
    assertStaticFragment(root.getChildren().get(2));
  }

  @Test
  void multiComponentPage() {
    JsoupTemplateParser parser = JsoupTemplateParser.instance();
    String content = loader.loadBlocking("/pages/multiComponentPage.html");
    FragmentGraph graph = parser.parse(content);

    RootFragment root = assertRoot(graph, 7);
    assertStaticFragment(root.getChildren().get(0));
    assertComponent(root.getChildren().get(1), "hello", "World");
    assertStaticFragment(root.getChildren().get(2));
    assertComponent(root.getChildren().get(3), "hello", "World");
    assertStaticFragment(root.getChildren().get(4));
    assertComponent(root.getChildren().get(5), "hello", "World");
    assertStaticFragment(root.getChildren().get(6));
  }

  @Test
  void simpleNestedComponentPage() {
    JsoupTemplateParser parser = JsoupTemplateParser.instance();
    String content = loader.loadBlocking("/pages/simpleNestedComponentPage.html");
    FragmentGraph graph = parser.parse(content);

    RootFragment root = assertRoot(graph, 3);
    ;
    ComponentFragment componentParent = assertComponent(root.getChildren().get(1), "conditional", "true");
    assertThat(componentParent.getSections()).hasSize(1);
    ComponentSectionFragment section = assertSection(componentParent.getSections().get(0));
    assertThat(section.getChildren()).hasSize(1);
    ComponentFragment innerComponent = assertComponent(section.getChildren().get(0), "hello", "World");
  }

  @Test
  void complexNestedComponentPage() {
    JsoupTemplateParser parser = JsoupTemplateParser.instance();
    String content = loader.loadBlocking("/pages/complexNestedComponentPage.html");
    FragmentGraph graph = parser.parse(content);

    RootFragment root = assertRoot(graph, 3);
    ComponentFragment componentParent = assertComponent(root.getChildren().get(1), "conditional", "true");
    assertThat(componentParent.getSections()).hasSize(1);
    ComponentSectionFragment section = assertSection(componentParent.getSections().get(0));
    assertThat(section.getChildren()).hasSize(3);
    ComponentFragment innerComponent = assertComponent(section.getChildren().get(1), "hello", "World");
  }

  @Test
  void multipleSectionsInComponent() {
    JsoupTemplateParser parser = JsoupTemplateParser.instance();
    String content = loader.loadBlocking("/pages/multipleSectionsInComponentPage.html");
    FragmentGraph graph = parser.parse(content);

    RootFragment root = assertRoot(graph, 3);
    assertStaticFragment(root.getChildren().get(0));
    ComponentFragment componentFragment = assertComponent(root.getChildren().get(1), "hello", "World");
    assertThat(componentFragment.getSections()).hasSize(2);
    assertSection(componentFragment.getSections().get(0), "sectionA");
    assertSection(componentFragment.getSections().get(1), "sectionB");
    assertStaticFragment(root.getChildren().get(2));
  }

  @Test
  void singleComponentInHeadPage() {
    JsoupTemplateParser parser = JsoupTemplateParser.instance();
    String content = loader.loadBlocking("/pages/componentInHead.html");
    FragmentGraph graph = parser.parse(content);

    RootFragment root = assertRoot(graph, 3);
    assertStaticFragment(root.getChildren().get(0));
    assertComponent(root.getChildren().get(1), "metadata", "");
    assertStaticFragment(root.getChildren().get(2));
  }

  private RootFragment assertRoot(FragmentGraph fragment, int size) {
    assertThat(fragment).isInstanceOf(RootFragment.class);
    RootFragment root = (RootFragment) fragment;
    assertThat(root.getChildren()).hasSize(size);
    return root;
  }

  private StaticFragment assertStaticFragment(FragmentGraph fragment) {
    assertThat(fragment).isInstanceOf(StaticFragment.class);
    return (StaticFragment) fragment;
  }

  private ComponentFragment assertComponent(FragmentGraph fragment, String componentId, String props) {
    assertThat(fragment).isInstanceOf(ComponentFragment.class);
    ComponentFragment componentChild = (ComponentFragment) fragment;
    assertThat(componentChild.getComponentId()).isEqualTo(componentId);
    assertThat(componentChild.getProps()).isEqualTo(props);
    return componentChild;
  }

  private ComponentSectionFragment assertSection(ComponentSectionFragment fragment, String name) {
    assertThat(fragment.getName()).isEqualTo(name);
    return fragment;
  }

  private ComponentSectionFragment assertSection(ComponentSectionFragment fragment) {
    return assertSection(fragment, DEFAULT_SECTION_NAME);
  }

}
