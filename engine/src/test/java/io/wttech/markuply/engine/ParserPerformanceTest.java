package io.wttech.markuply.engine;

import io.wttech.markuply.engine.template.parser.atto.AttoTemplateParser;
import io.wttech.markuply.engine.template.parser.jsoup.JsoupTemplateParser;
import io.wttech.markuply.engine.utils.ClasspathFileLoader;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@Fork(value = 1, warmups = 1)
@Warmup(iterations = 5, time = 5000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 5000, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@BenchmarkMode(Mode.AverageTime)
public class ParserPerformanceTest {

  @State(Scope.Benchmark)
  public static class BenchmarkState {

    public AttoTemplateParser atto = AttoTemplateParser.instance();
    public JsoupTemplateParser jsoup = JsoupTemplateParser.instance();
    public String template = ClasspathFileLoader.instance().loadBlocking("/pages/performance.html");

  }

  @Benchmark
  public void atto(Blackhole blackhole, BenchmarkState state) {
    blackhole.consume(state.atto.parse(state.template));
  }

  @Benchmark
  public void jsoup(Blackhole blackhole, BenchmarkState state) {
    blackhole.consume(state.jsoup.parse(state.template));
  }

}
