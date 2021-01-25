package com.wundermanthompson.markuply.engine.component.method.function;

/**
 * Same as {@link java.util.function.BiFunction} but with 3 arguments.
 * @param <T> returned type
 * @param <U> first argument type
 * @param <V> second argument type
 * @param <R> third argument type
 */
public interface TriFunction<T, U, V, R> {

  R apply(T arg1, U arg2, V arg3);

}
