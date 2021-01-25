package com.wundermanthompson.markuply.engine.template.parser.atto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DepthCounter {

  private int depth;

  public static DepthCounter instance() {
    return new DepthCounter(0);
  }

  public static DepthCounter instance(int initial) {
    return new DepthCounter(initial);
  }

  public void increment() {
    depth += 1;
  }

  public void decrement() {
    depth -= 1;
  }

  public boolean isZero() {
    return depth == 0;
  }

  public boolean is(int value) {
    return depth == value;
  }

  public int get() {
    return depth;
  }

}
