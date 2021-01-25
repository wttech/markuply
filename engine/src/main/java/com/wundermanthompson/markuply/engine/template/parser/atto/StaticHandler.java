package com.wundermanthompson.markuply.engine.template.parser.atto;

import com.wundermanthompson.markuply.engine.template.graph.node.StaticFragment;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.output.StringBuilderWriter;
import org.attoparser.AbstractMarkupHandler;
import org.attoparser.ParseException;
import org.attoparser.output.OutputMarkupHandler;

import java.util.Optional;

@RequiredArgsConstructor(staticName = "instance")
public class StaticHandler extends AbstractMarkupHandler {

  private final StringBuilder staticPartBuilder = new StringBuilder();
  private final OutputMarkupHandler outputHandler = new OutputMarkupHandler(new StringBuilderWriter(staticPartBuilder));

  public Optional<StaticFragment> getResult() {
    return staticPartBuilder.length() > 0 ? Optional.of(StaticFragment.of(staticPartBuilder.toString())) : Optional.empty();
  }

  public StringBuilder toStringBuilder() {
    return staticPartBuilder;
  }

  public void append(StaticHandler bufferHandler) {
    staticPartBuilder.append(bufferHandler.staticPartBuilder);
  }

  @Override
  public void handleDocumentStart(long startTimeNanos, int line, int col) throws ParseException {
    outputHandler.handleDocumentStart(startTimeNanos, line, col);
  }

  @Override
  public void handleDocumentEnd(long endTimeNanos, long totalTimeNanos, int line, int col) throws ParseException {
    outputHandler.handleDocumentEnd(endTimeNanos, totalTimeNanos, line, col);
  }

  @Override
  public void handleXmlDeclaration(char[] buffer, int keywordOffset, int keywordLen, int keywordLine, int keywordCol, int versionOffset, int versionLen, int versionLine, int versionCol, int encodingOffset, int encodingLen, int encodingLine, int encodingCol, int standaloneOffset, int standaloneLen, int standaloneLine, int standaloneCol, int outerOffset, int outerLen, int line, int col) throws ParseException {
    outputHandler.handleXmlDeclaration(buffer, keywordOffset, keywordLen, keywordLine, keywordCol, versionOffset, versionLen, versionLine, versionCol, encodingOffset, encodingLen, encodingLine, encodingCol, standaloneOffset, standaloneLen, standaloneLine, standaloneCol, outerOffset, outerLen, line, col);
  }

  @Override
  public void handleDocType(char[] buffer, int keywordOffset, int keywordLen, int keywordLine, int keywordCol, int elementNameOffset, int elementNameLen, int elementNameLine, int elementNameCol, int typeOffset, int typeLen, int typeLine, int typeCol, int publicIdOffset, int publicIdLen, int publicIdLine, int publicIdCol, int systemIdOffset, int systemIdLen, int systemIdLine, int systemIdCol, int internalSubsetOffset, int internalSubsetLen, int internalSubsetLine, int internalSubsetCol, int outerOffset, int outerLen, int outerLine, int outerCol) throws ParseException {
    outputHandler.handleDocType(buffer, keywordOffset, keywordLen, keywordLine, keywordCol, elementNameOffset, elementNameLen, elementNameLine, elementNameCol, typeOffset, typeLen, typeLine, typeCol, publicIdOffset, publicIdLen, publicIdLine, publicIdCol, systemIdOffset, systemIdLen, systemIdLine, systemIdCol, internalSubsetOffset, internalSubsetLen, internalSubsetLine, internalSubsetCol, outerOffset, outerLen, outerLine, outerCol);
  }

  @Override
  public void handleCDATASection(char[] buffer, int contentOffset, int contentLen, int outerOffset, int outerLen, int line, int col) throws ParseException {
    outputHandler.handleCDATASection(buffer, contentOffset, contentLen, outerOffset, outerLen, line, col);
  }

  @Override
  public void handleComment(char[] buffer, int contentOffset, int contentLen, int outerOffset, int outerLen, int line, int col) throws ParseException {
    outputHandler.handleComment(buffer, contentOffset, contentLen, outerOffset, outerLen, line, col);
  }

  @Override
  public void handleText(char[] buffer, int offset, int len, int line, int col) throws ParseException {
    outputHandler.handleText(buffer, offset, len, line, col);
  }

  @Override
  public void handleStandaloneElementStart(char[] buffer, int nameOffset, int nameLen, boolean minimized, int line, int col) throws ParseException {
    outputHandler.handleStandaloneElementStart(buffer, nameOffset, nameLen, minimized, line, col);
  }

  @Override
  public void handleStandaloneElementEnd(char[] buffer, int nameOffset, int nameLen, boolean minimized, int line, int col) throws ParseException {
    outputHandler.handleStandaloneElementEnd(buffer, nameOffset, nameLen, minimized, line, col);
  }

  @Override
  public void handleOpenElementStart(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    outputHandler.handleOpenElementStart(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleOpenElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    outputHandler.handleOpenElementEnd(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleAutoOpenElementStart(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    outputHandler.handleAutoOpenElementStart(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleAutoOpenElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    outputHandler.handleAutoOpenElementEnd(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleCloseElementStart(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    outputHandler.handleCloseElementStart(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleCloseElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    outputHandler.handleCloseElementEnd(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleAutoCloseElementStart(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    outputHandler.handleAutoCloseElementStart(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleAutoCloseElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    outputHandler.handleAutoCloseElementEnd(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleUnmatchedCloseElementStart(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    outputHandler.handleUnmatchedCloseElementStart(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleUnmatchedCloseElementEnd(char[] buffer, int nameOffset, int nameLen, int line, int col) throws ParseException {
    outputHandler.handleUnmatchedCloseElementEnd(buffer, nameOffset, nameLen, line, col);
  }

  @Override
  public void handleAttribute(char[] buffer, int nameOffset, int nameLen, int nameLine, int nameCol, int operatorOffset, int operatorLen, int operatorLine, int operatorCol, int valueContentOffset, int valueContentLen, int valueOuterOffset, int valueOuterLen, int valueLine, int valueCol) throws ParseException {
    outputHandler.handleAttribute(buffer, nameOffset, nameLen, nameLine, nameCol, operatorOffset, operatorLen, operatorLine, operatorCol, valueContentOffset, valueContentLen, valueOuterOffset, valueOuterLen, valueLine, valueCol);
  }

  @Override
  public void handleInnerWhiteSpace(char[] buffer, int offset, int len, int line, int col) throws ParseException {
    outputHandler.handleInnerWhiteSpace(buffer, offset, len, line, col);
  }

  @Override
  public void handleProcessingInstruction(char[] buffer, int targetOffset, int targetLen, int targetLine, int targetCol, int contentOffset, int contentLen, int contentLine, int contentCol, int outerOffset, int outerLen, int line, int col) throws ParseException {
    outputHandler.handleProcessingInstruction(buffer, targetOffset, targetLen, targetLine, targetCol, contentOffset, contentLen, contentLine, contentCol, outerOffset, outerLen, line, col);
  }
}
