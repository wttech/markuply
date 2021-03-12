package io.wttech.markuply.engine.template.parser.atto;

import lombok.RequiredArgsConstructor;

import java.util.ArrayDeque;
import java.util.Deque;

@RequiredArgsConstructor
public class StackContext {

  private final Deque<FragmentHandler> handlers;

  public static StackContext instance() {
    return new StackContext(new ArrayDeque<>());
  }

  public void push(FragmentHandler handler) {
    handlers.push(handler);
  }

  public FragmentHandler peek() {
    return handlers.peek();
  }

  public void pop() {
    FragmentHandler lastHandler = handlers.pop();
    FragmentHandler newLast = handlers.peek();
    lastHandler.reportEndToParent(newLast);
  }

}
