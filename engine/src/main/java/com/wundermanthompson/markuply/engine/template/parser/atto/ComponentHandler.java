package com.wundermanthompson.markuply.engine.template.parser.atto;

import com.wundermanthompson.markuply.engine.template.graph.node.ComponentFragment;
import com.wundermanthompson.markuply.engine.template.graph.node.ComponentSectionFragment;
import com.wundermanthompson.markuply.engine.template.parser.TemplateParserException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.attoparser.AbstractMarkupHandler;
import org.attoparser.IMarkupHandler;
import org.attoparser.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ComponentHandler extends AbstractMarkupHandler implements FragmentHandler {

  private final String name;
  private final String props;
  private final DepthCounter counter = DepthCounter.instance(1);
  private final StackContext stackContext;

  private final DepthlessContentHandler depthlessContentHandler = DepthlessContentHandler.instance();
  private StaticHandler bufferHandler;
  private IMarkupHandler targetHandler = depthlessContentHandler;

  private final List<ComponentSectionFragment> sections = new ArrayList<>();

  private final MarkuplyAttributesWrapper attributes = MarkuplyAttributesWrapper.instance();

  public static ComponentHandler instance(String name, String props, StackContext stack) {
    return new ComponentHandler(name, props, stack);
  }

  public ComponentFragment getResult() {
    List<ComponentSectionFragment> finalSections = !sections.isEmpty()
        ? sections
        : Collections.singletonList(ComponentSectionFragment.instance(depthlessContentHandler.getResult()));
    return ComponentFragment.of(name, props, finalSections);
  }

  private void startUndeterminedMode() {
    bufferHandler = StaticHandler.instance();
    targetHandler = bufferHandler;
  }

  private void startStaticMode() {
    targetHandler = depthlessContentHandler;
  }

  private void undeterminedToStaticMode() {
    depthlessContentHandler.append(bufferHandler);
    targetHandler = depthlessContentHandler;
  }

  private void startSectionMode() {
    endStaticMode();
    String sectionName = attributes.getSectionName();
    stackContext.push(ComponentSectionHandler.instance(sectionName, stackContext));
    startStaticMode();
  }

  private void startComponentMode() {
    endStaticMode();
    String componentId = attributes.getComponentId();
    String props = attributes.getProps();
    stackContext.push(ComponentHandler.instance(componentId, props, stackContext));
    startStaticMode();
  }

  private void processSimpleComponent() {
    endStaticMode();
    String componentId = attributes.getComponentId();
    String props = attributes.getProps();
    depthlessContentHandler.append(ComponentFragment.simple(props, componentId));
    startStaticMode();
  }

  private void endStaticMode() {
    depthlessContentHandler.endStatic();
  }

  @Override
  public void handleInnerResult(ComponentHandler handler) {
    depthlessContentHandler.append(handler.getResult());
  }

  @Override
  public void handleInnerResult(ComponentSectionHandler handler) {
    sections.add(handler.getResult());
  }

  @Override
  public void reportEndToParent(FragmentHandler handler) {
    handler.handleInnerResult(this);
  }

  @Override
  public void handleDocumentStart(long startTimeNanos, int line, int col) throws ParseException {
    targetHandler.handleDocumentStart(startTimeNanos, line, col);
  }

  @Override
  public void handleDocumentEnd(long endTimeNanos, long totalTimeNanos, int line, int col) throws ParseException {
    targetHandler.handleDocumentEnd(endTimeNanos, totalTimeNanos, line, col);
  }

  @Override
  public void handleXmlDeclaration(char[] buffer, int keywordOffset, int keywordLen, int keywordLine, int keywordCol, int versionOffset, int versionLen, int versionLine, int versionCol, int encodingOffset, int encodingLen, int encodingLine, int encodingCol, int standaloneOffset, int standaloneLen, int standaloneLine, int standaloneCol, int outerOffset, int outerLen, int line, int col) throws ParseException {
    targetHandler.handleXmlDeclaration(buffer, keywordOffset, keywordLen, keywordLine, keywordCol, versionOffset, versionLen, versionLine, versionCol, encodingOffset, encodingLen, encodingLine, encodingCol, standaloneOffset, standaloneLen, standaloneLine, standaloneCol, outerOffset, outerLen, line, col);
  }

  @Override
  public void handleDocType(char[] buffer, int keywordOffset, int keywordLen, int keywordLine, int keywordCol, int elementNameOffset, int elementNameLen, int elementNameLine, int elementNameCol, int typeOffset, int typeLen, int typeLine, int typeCol, int publicIdOffset, int publicIdLen, int publicIdLine, int publicIdCol, int systemIdOffset, int systemIdLen, int systemIdLine, int systemIdCol, int internalSubsetOffset, int internalSubsetLen, int internalSubsetLine, int internalSubsetCol, int outerOffset, int outerLen, int outerLine, int outerCol) throws ParseException {
    targetHandler.handleDocType(buffer, keywordOffset, keywordLen, keywordLine, keywordCol, elementNameOffset, elementNameLen, elementNameLine, elementNameCol, typeOffset, typeLen, typeLine, typeCol, publicIdOffset, publicIdLen, publicIdLine, publicIdCol, systemIdOffset, systemIdLen, systemIdLine, systemIdCol, internalSubsetOffset, internalSubsetLen, internalSubsetLine, internalSubsetCol, outerOffset, outerLen, outerLine, outerCol);
  }

  @Override
  public void handleCDATASection(char[] buffer, int contentOffset, int contentLen, int outerOffset, int outerLen, int line, int col) throws ParseException {
    targetHandler.handleCDATASection(buffer, contentOffset, contentLen, outerOffset, outerLen, line, col);
  }

  @Override
  public void handleComment(char[] buffer, int contentOffset, int contentLen, int outerOffset, int outerLen, int line, int col) throws ParseException {
    targetHandler.handleComment(buffer, contentOffset, contentLen, outerOffset, outerLen, line, col);
  }

  @Override
  public void handleText(char[] buffer, int offset, int len, int line, int col) throws ParseException {
    targetHandler.handleText(buffer, offset, len, line, col);
  }

  @Override
  public void handleStandaloneElementStart(char[] buffer, int nameOffset, int nameLen, boolean minimized, int line, int col) throws ParseException {
    startUndeterminedMode();
    targetHandler.handleStandaloneElementStart(buffer, nameOffset, nameLen, minimized, line, col);
  }

  @Override
  public void handleStandaloneElementEnd(char[] buffer, int nameOffset, int nameLen, boolean minimized, int line, int col) throws ParseException {
    targetHandler.handleStandaloneElementEnd(buffer, nameOffset, nameLen, minimized, line, col);
    // check attributes, is it a component?
    if (attributes.isComponentAttributePresent()) {
      processSimpleComponent();
    } else {
      undeterminedToStaticMode();
    }
    attributes.clear();
  }

  @Override
  public void handleOpenElementStart(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    startUndeterminedMode();
    counter.increment();
    targetHandler.handleOpenElementStart(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleOpenElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    targetHandler.handleOpenElementEnd(buffer, nameOffset, nameLen, line, col);
    if (attributes.isComponentAttributePresent()) {
      startComponentMode();
      counter.decrement();
    } else if (attributes.isSectionAttributePresent()) {
      if (!counter.is(2)) {
        throw new TemplateParserException("Component section can be defined only as a direct child of a component element. Depth: " + counter.get());
      }
      startSectionMode();
      counter.decrement();
    } else {
      undeterminedToStaticMode();
    }
    attributes.clear();
  }

  @Override
  public void handleAutoOpenElementStart(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    targetHandler.handleAutoOpenElementStart(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleAutoOpenElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    targetHandler.handleAutoOpenElementEnd(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleCloseElementStart(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    counter.decrement();
    if (!counter.isZero()) {
      targetHandler.handleCloseElementStart(buffer, nameOffset, nameLen, line, col);
    }
  }

  @Override
  public void handleCloseElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    if (!counter.isZero()) {
      targetHandler.handleCloseElementEnd(buffer, nameOffset, nameLen, line, col);
    } else {
      endStaticMode();
      stackContext.pop();
    }
  }

  @Override
  public void handleAutoCloseElementStart(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    targetHandler.handleAutoCloseElementStart(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleAutoCloseElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    targetHandler.handleAutoCloseElementEnd(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleUnmatchedCloseElementStart(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    counter.decrement();
    if (!counter.isZero()) {
      targetHandler.handleUnmatchedCloseElementStart(buffer, nameOffset, nameLen, line, col);
    }
  }

  @Override
  public void handleUnmatchedCloseElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    if (!counter.isZero()) {
      targetHandler.handleUnmatchedCloseElementEnd(buffer, nameOffset, nameLen, line, col);
    } else {
      endStaticMode();
      stackContext.pop();
    }
  }

  @Override
  public void handleAttribute(char[] buffer, int nameOffset, int nameLen, int nameLine, int nameCol, int operatorOffset, int operatorLen, int operatorLine, int operatorCol, int valueContentOffset, int valueContentLen, int valueOuterOffset, int valueOuterLen, int valueLine, int valueCol) throws ParseException {
    targetHandler.handleAttribute(buffer, nameOffset, nameLen, nameLine, nameCol, operatorOffset, operatorLen, operatorLine, operatorCol, valueContentOffset, valueContentLen, valueOuterOffset, valueOuterLen, valueLine, valueCol);
    attributes.extractValidAttribute(buffer, nameOffset, nameLen, valueContentOffset, valueContentLen);
  }

  @Override
  public void handleInnerWhiteSpace(char[] buffer, int offset, int len, int line, int col) throws ParseException {
    targetHandler.handleInnerWhiteSpace(buffer, offset, len, line, col);
  }

  @Override
  public void handleProcessingInstruction(char[] buffer, int targetOffset, int targetLen, int targetLine, int targetCol, int contentOffset, int contentLen, int contentLine, int contentCol, int outerOffset, int outerLen, int line, int col) throws ParseException {
    targetHandler.handleProcessingInstruction(buffer, targetOffset, targetLen, targetLine, targetCol, contentOffset, contentLen, contentLine, contentCol, outerOffset, outerLen, line, col);
  }
}
