package io.wttech.markuply.engine.template.parser.atto;

import io.wttech.markuply.engine.template.graph.node.ComponentFragment;
import io.wttech.markuply.engine.template.graph.node.RootFragment;
import io.wttech.markuply.engine.template.parser.TemplateParserConfiguration;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.attoparser.AbstractMarkupHandler;
import org.attoparser.IMarkupHandler;
import org.attoparser.ParseException;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RootHandler extends AbstractMarkupHandler implements FragmentHandler {

  private final StackContext stackContext;

  private final DepthlessContentHandler depthlessContentHandler;
  private final TemplateParserConfiguration configuration;
  private StaticHandler bufferHandler;
  private IMarkupHandler targetHandler;

  private final MarkuplyAttributesWrapper attributes;

  public static RootHandler instance(StackContext stack, TemplateParserConfiguration configuration) {
    return new RootHandler(stack, DepthlessContentHandler.instance(), configuration, MarkuplyAttributesWrapper.instance(configuration));
  }

  public RootFragment getResult() {
    return RootFragment.of(depthlessContentHandler.getResult());
  }

  @Override
  public void handleInnerResult(ComponentHandler handler) {
    depthlessContentHandler.append(handler.getResult());
  }

  @Override
  public void handleInnerResult(ComponentSectionHandler handler) {
    throw new IllegalStateException("Section must be a direct child of a component");
  }

  @Override
  public void reportEndToParent(FragmentHandler handler) {
    throw new IllegalStateException("This is root. It cannot have a parent.");
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

  private void startComponentMode() {
    endStaticMode();
    String componentId = attributes.getComponentId();
    String props = attributes.getProps();
    stackContext.push(ComponentHandler.instance(componentId, props, stackContext, configuration));
    startStaticMode();
  }

  private void processSimpleComponent() {
    endStaticMode();
    String componentId = attributes.getComponentId();
    String props = attributes.getProps();
    depthlessContentHandler.append(ComponentFragment.simple(componentId, props));
    startStaticMode();
  }

  private void endStaticMode() {
    depthlessContentHandler.endStatic();
  }

  @Override
  public void handleDocumentStart(long startTimeNanos, int line, int col) throws ParseException {
    startStaticMode();
    targetHandler.handleDocumentStart(startTimeNanos, line, col);
  }

  @Override
  public void handleDocumentEnd(long endTimeNanos, long totalTimeNanos, int line, int col) throws ParseException {
    targetHandler.handleDocumentEnd(endTimeNanos, totalTimeNanos, line, col);
    endStaticMode();
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
    targetHandler.handleOpenElementStart(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleOpenElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    targetHandler.handleOpenElementEnd(buffer, nameOffset, nameLen, line, col);
    if (attributes.isComponentAttributePresent()) {
      startComponentMode();
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
    targetHandler.handleCloseElementStart(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleCloseElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    targetHandler.handleCloseElementEnd(buffer, nameOffset, nameLen, line, col);
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
    targetHandler.handleUnmatchedCloseElementStart(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleUnmatchedCloseElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    targetHandler.handleUnmatchedCloseElementEnd(buffer, nameOffset, nameLen, line, col);
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
