package io.wttech.markuply.engine.component.method.function;

/**
 * Created only so there's no need for additional logic in LambdaComponentFactory as {@link java.util.function.Supplier} defines the "get" method instead of "apply".
 *
 * @param <T> returned value type
 */
public interface UnifiedSupplier<T> {

  T apply();

}
