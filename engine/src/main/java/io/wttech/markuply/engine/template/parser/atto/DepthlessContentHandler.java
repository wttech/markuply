package io.wttech.markuply.engine.template.parser.atto;

import io.wttech.markuply.engine.template.graph.node.FragmentGraph;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.attoparser.AbstractMarkupHandler;
import org.attoparser.ParseException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DepthlessContentHandler extends AbstractMarkupHandler {

  private final List<FragmentGraph> children;

  private StaticHandler staticHandler = StaticHandler.instance();

  public List<FragmentGraph> getResult() {
    return children;
  }

  public static DepthlessContentHandler instance() {
    return new DepthlessContentHandler(new ArrayList<>());
  }

  public void append(StaticHandler bufferHandler) {
    staticHandler.append(bufferHandler);
  }

  public void append(FragmentGraph fragment) {
    children.add(fragment);
  }

  public void endStatic() {
    staticHandler.getResult().ifPresent(children::add);
    staticHandler = StaticHandler.instance();
  }

  @Override
  public void handleDocumentStart(long startTimeNanos, int line, int col) throws ParseException {
    staticHandler.handleDocumentStart(startTimeNanos, line, col);
  }

  @Override
  public void handleDocumentEnd(long endTimeNanos, long totalTimeNanos, int line, int col) throws ParseException {
    staticHandler.handleDocumentEnd(endTimeNanos, totalTimeNanos, line, col);
  }

  @Override
  public void handleXmlDeclaration(char[] buffer, int keywordOffset, int keywordLen, int keywordLine, int keywordCol, int versionOffset, int versionLen, int versionLine, int versionCol, int encodingOffset, int encodingLen, int encodingLine, int encodingCol, int standaloneOffset, int standaloneLen, int standaloneLine, int standaloneCol, int outerOffset, int outerLen, int line, int col) throws ParseException {
    staticHandler.handleXmlDeclaration(buffer, keywordOffset, keywordLen, keywordLine, keywordCol, versionOffset, versionLen, versionLine, versionCol, encodingOffset, encodingLen, encodingLine, encodingCol, standaloneOffset, standaloneLen, standaloneLine, standaloneCol, outerOffset, outerLen, line, col);
  }

  @Override
  public void handleDocType(char[] buffer, int keywordOffset, int keywordLen, int keywordLine, int keywordCol, int elementNameOffset, int elementNameLen, int elementNameLine, int elementNameCol, int typeOffset, int typeLen, int typeLine, int typeCol, int publicIdOffset, int publicIdLen, int publicIdLine, int publicIdCol, int systemIdOffset, int systemIdLen, int systemIdLine, int systemIdCol, int internalSubsetOffset, int internalSubsetLen, int internalSubsetLine, int internalSubsetCol, int outerOffset, int outerLen, int outerLine, int outerCol) throws ParseException {
    staticHandler.handleDocType(buffer, keywordOffset, keywordLen, keywordLine, keywordCol, elementNameOffset, elementNameLen, elementNameLine, elementNameCol, typeOffset, typeLen, typeLine, typeCol, publicIdOffset, publicIdLen, publicIdLine, publicIdCol, systemIdOffset, systemIdLen, systemIdLine, systemIdCol, internalSubsetOffset, internalSubsetLen, internalSubsetLine, internalSubsetCol, outerOffset, outerLen, outerLine, outerCol);
  }

  @Override
  public void handleCDATASection(char[] buffer, int contentOffset, int contentLen, int outerOffset, int outerLen, int line, int col) throws ParseException {
    staticHandler.handleCDATASection(buffer, contentOffset, contentLen, outerOffset, outerLen, line, col);
  }

  @Override
  public void handleComment(char[] buffer, int contentOffset, int contentLen, int outerOffset, int outerLen, int line, int col) throws ParseException {
    staticHandler.handleComment(buffer, contentOffset, contentLen, outerOffset, outerLen, line, col);
  }

  @Override
  public void handleText(char[] buffer, int offset, int len, int line, int col) throws ParseException {
    staticHandler.handleText(buffer, offset, len, line, col);
  }

  @Override
  public void handleStandaloneElementStart(char[] buffer, int nameOffset, int nameLen, boolean minimized, int line, int col) throws ParseException {
    staticHandler.handleStandaloneElementStart(buffer, nameOffset, nameLen, minimized, line, col);
  }

  @Override
  public void handleStandaloneElementEnd(char[] buffer, int nameOffset, int nameLen, boolean minimized, int line, int col) throws ParseException {
    staticHandler.handleStandaloneElementEnd(buffer, nameOffset, nameLen, minimized, line, col);
  }

  @Override
  public void handleOpenElementStart(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    staticHandler.handleOpenElementStart(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleOpenElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    staticHandler.handleOpenElementEnd(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleAutoOpenElementStart(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    staticHandler.handleAutoOpenElementStart(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleAutoOpenElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    staticHandler.handleAutoOpenElementEnd(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleCloseElementStart(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    staticHandler.handleCloseElementStart(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleCloseElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    staticHandler.handleCloseElementEnd(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleAutoCloseElementStart(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    staticHandler.handleAutoCloseElementStart(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleAutoCloseElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    staticHandler.handleAutoCloseElementEnd(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleUnmatchedCloseElementStart(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    staticHandler.handleUnmatchedCloseElementStart(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleUnmatchedCloseElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    staticHandler.handleUnmatchedCloseElementEnd(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleAttribute(char[] buffer, int nameOffset, int nameLen, int nameLine, int nameCol, int operatorOffset, int operatorLen, int operatorLine, int operatorCol, int valueContentOffset, int valueContentLen, int valueOuterOffset, int valueOuterLen, int valueLine, int valueCol) throws ParseException {
    staticHandler.handleAttribute(buffer, nameOffset, nameLen, nameLine, nameCol, operatorOffset, operatorLen, operatorLine, operatorCol, valueContentOffset, valueContentLen, valueOuterOffset, valueOuterLen, valueLine, valueCol);
  }

  @Override
  public void handleInnerWhiteSpace(char[] buffer, int offset, int len, int line, int col) throws ParseException {
    staticHandler.handleInnerWhiteSpace(buffer, offset, len, line, col);
  }

  @Override
  public void handleProcessingInstruction(char[] buffer, int targetOffset, int targetLen, int targetLine, int targetCol, int contentOffset, int contentLen, int contentLine, int contentCol, int outerOffset, int outerLen, int line, int col) throws ParseException {
    staticHandler.handleProcessingInstruction(buffer, targetOffset, targetLen, targetLine, targetCol, contentOffset, contentLen, contentLine, contentCol, outerOffset, outerLen, line, col);
  }

}
