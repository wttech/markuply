package com.wundermanthompson.markuply.engine.template.parser.atto;

import com.wundermanthompson.markuply.engine.template.graph.node.RootFragment;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.attoparser.AbstractMarkupHandler;
import org.attoparser.ParseException;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class StackHandler extends AbstractMarkupHandler {

  private RootHandler rootHandler;
  private final StackContext stackContext;

  public static StackHandler instance() {
    return new StackHandler(StackContext.instance());
  }

  private void startRootMode() {
    rootHandler = RootHandler.instance(stackContext);
    stackContext.push(rootHandler);
  }

  public RootFragment getResult() {
    return rootHandler.getResult();
  }

  private FragmentHandler getTopHandler() {
    return stackContext.peek();
  }

  @Override
  public void handleDocumentStart(long startTimeNanos, int line, int col) throws ParseException {
    startRootMode();
    getTopHandler().handleDocumentStart(startTimeNanos, line, col);
  }

  @Override
  public void handleDocumentEnd(long endTimeNanos, long totalTimeNanos, int line, int col) throws ParseException {
    getTopHandler().handleDocumentEnd(endTimeNanos, totalTimeNanos, line, col);
  }

  @Override
  public void handleXmlDeclaration(char[] buffer, int keywordOffset, int keywordLen, int keywordLine, int keywordCol, int versionOffset, int versionLen, int versionLine, int versionCol, int encodingOffset, int encodingLen, int encodingLine, int encodingCol, int standaloneOffset, int standaloneLen, int standaloneLine, int standaloneCol, int outerOffset, int outerLen, int line, int col) throws ParseException {
    getTopHandler().handleXmlDeclaration(buffer, keywordOffset, keywordLen, keywordLine, keywordCol, versionOffset, versionLen, versionLine, versionCol, encodingOffset, encodingLen, encodingLine, encodingCol, standaloneOffset, standaloneLen, standaloneLine, standaloneCol, outerOffset, outerLen, line, col);
  }

  @Override
  public void handleDocType(char[] buffer, int keywordOffset, int keywordLen, int keywordLine, int keywordCol, int elementNameOffset, int elementNameLen, int elementNameLine, int elementNameCol, int typeOffset, int typeLen, int typeLine, int typeCol, int publicIdOffset, int publicIdLen, int publicIdLine, int publicIdCol, int systemIdOffset, int systemIdLen, int systemIdLine, int systemIdCol, int internalSubsetOffset, int internalSubsetLen, int internalSubsetLine, int internalSubsetCol, int outerOffset, int outerLen, int outerLine, int outerCol) throws ParseException {
    getTopHandler().handleDocType(buffer, keywordOffset, keywordLen, keywordLine, keywordCol, elementNameOffset, elementNameLen, elementNameLine, elementNameCol, typeOffset, typeLen, typeLine, typeCol, publicIdOffset, publicIdLen, publicIdLine, publicIdCol, systemIdOffset, systemIdLen, systemIdLine, systemIdCol, internalSubsetOffset, internalSubsetLen, internalSubsetLine, internalSubsetCol, outerOffset, outerLen, outerLine, outerCol);
  }

  @Override
  public void handleCDATASection(char[] buffer, int contentOffset, int contentLen, int outerOffset, int outerLen, int line, int col) throws ParseException {
    getTopHandler().handleCDATASection(buffer, contentOffset, contentLen, outerOffset, outerLen, line, col);
  }

  @Override
  public void handleComment(char[] buffer, int contentOffset, int contentLen, int outerOffset, int outerLen, int line, int col) throws ParseException {
    getTopHandler().handleComment(buffer, contentOffset, contentLen, outerOffset, outerLen, line, col);
  }

  @Override
  public void handleText(char[] buffer, int offset, int len, int line, int col) throws ParseException {
    getTopHandler().handleText(buffer, offset, len, line, col);
  }

  @Override
  public void handleStandaloneElementStart(char[] buffer, int nameOffset, int nameLen, boolean minimized, int line, int col) throws ParseException {
    getTopHandler().handleStandaloneElementStart(buffer, nameOffset, nameLen, minimized, line, col);
  }

  @Override
  public void handleStandaloneElementEnd(char[] buffer, int nameOffset, int nameLen, boolean minimized, int line, int col) throws ParseException {
    getTopHandler().handleStandaloneElementEnd(buffer, nameOffset, nameLen, minimized, line, col);
  }

  @Override
  public void handleOpenElementStart(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    getTopHandler().handleOpenElementStart(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleOpenElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    getTopHandler().handleOpenElementEnd(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleAutoOpenElementStart(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    getTopHandler().handleAutoOpenElementStart(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleAutoOpenElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    getTopHandler().handleAutoOpenElementEnd(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleCloseElementStart(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    getTopHandler().handleCloseElementStart(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleCloseElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    getTopHandler().handleCloseElementEnd(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleAutoCloseElementStart(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    getTopHandler().handleAutoCloseElementStart(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleAutoCloseElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    getTopHandler().handleAutoCloseElementEnd(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleUnmatchedCloseElementStart(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    getTopHandler().handleUnmatchedCloseElementStart(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleUnmatchedCloseElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    getTopHandler().handleUnmatchedCloseElementEnd(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleAttribute(char[] buffer, int nameOffset, int nameLen, int nameLine, int nameCol, int operatorOffset, int operatorLen, int operatorLine, int operatorCol, int valueContentOffset, int valueContentLen, int valueOuterOffset, int valueOuterLen, int valueLine, int valueCol) throws ParseException {
    getTopHandler().handleAttribute(buffer, nameOffset, nameLen, nameLine, nameCol, operatorOffset, operatorLen, operatorLine, operatorCol, valueContentOffset, valueContentLen, valueOuterOffset, valueOuterLen, valueLine, valueCol);
  }

  @Override
  public void handleInnerWhiteSpace(char[] buffer, int offset, int len, int line, int col) throws ParseException {
    getTopHandler().handleInnerWhiteSpace(buffer, offset, len, line, col);
  }

  @Override
  public void handleProcessingInstruction(char[] buffer, int targetOffset, int targetLen, int targetLine, int targetCol, int contentOffset, int contentLen, int contentLine, int contentCol, int outerOffset, int outerLen, int line, int col) throws ParseException {
    getTopHandler().handleProcessingInstruction(buffer, targetOffset, targetLen, targetLine, targetCol, contentOffset, contentLen, contentLine, contentCol, outerOffset, outerLen, line, col);
  }

}
