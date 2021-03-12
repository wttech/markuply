package io.wttech.markuply.engine.pipeline.context;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * Holder of page context objects keyed by their type.
 * </p>
 * <p>
 * Immutable.
 * </p>
 */
@ToString
@AllArgsConstructor
public class PageContext {

  private static final PageContext EMPTY_CONTEXT = PageContext.builder().build();

  private final Map<Class<?>, Object> context;

  /**
   * Creates a new page context builder.
   *
   * @return page context builder
   */
  public static Builder builder() {
    return Builder.instance();
  }

  /**
   * Creates a context without any values inside.
   *
   * @return context
   */
  public static PageContext empty() {
    return EMPTY_CONTEXT;
  }

  /**
   * Creates a new context with the provided value stored inside.
   *
   * @param value to be included in the context
   * @param <T>   value type
   * @return new immutable context
   */
  public static <T> PageContext of(T value) {
    return of((Class<T>) value.getClass(), value);
  }

  /**
   * Creates a new context with the provided value stored inside.
   *
   * @param clazz key under which value will be registered
   * @param value to be included in the context
   * @param <T>   value type
   * @return new immutable context
   */
  public static <T> PageContext of(Class<T> clazz, T value) {
    return PageContext.builder().put(clazz, value).build();
  }

  /**
   * Creates a new context with the provided values stored inside.
   *
   * @param value        to be included in the context
   * @param anotherValue to be included in the context
   * @param <T>          first value type
   * @param <T2>         second value type
   * @return new immutable context
   */
  public static <T, T2> PageContext of(T value, T2 anotherValue) {
    return of(
        (Class<T>) value.getClass(), value,
        (Class<T2>) anotherValue.getClass(), anotherValue
    );
  }

  /**
   * Creates a new context with the provided values stored inside.
   *
   * @param clazz        key under which the first value will be registered
   * @param value        to be included in the context
   * @param anotherClazz key under which the second value will be registered
   * @param anotherValue to be included in the context
   * @param <T>          first value type
   * @param <T2>         second value type
   * @return new immutable context
   */
  public static <T, T2> PageContext of(Class<T> clazz, T value, Class<T2> anotherClazz,
                                       T2 anotherValue) {
    return PageContext.builder()
        .put(clazz, value)
        .put(anotherClazz, anotherValue)
        .build();
  }

  /**
   * Retrieves the value registered under the provided class key.
   *
   * @param clazz class of the value to retrieve
   * @param <T>   value type
   * @return registered value
   * @throws IllegalArgumentException when there is no value registered for the provided class key
   */
  public <T> T get(Class<T> clazz) {
    return find(clazz)
        .orElseThrow(() -> new IllegalArgumentException("Request context of type " + clazz.getSimpleName() + " is not available"));
  }

  /**
   * Retrieves the value registered under the provided class key or an empty Optional if there is no such value.
   *
   * @param clazz class of the value to retrieve
   * @param <T>   value type
   * @return registered value optional
   */
  public <T> Optional<T> find(Class<T> clazz) {
    return Optional.ofNullable((T) context.get(clazz));
  }

  /**
   * Creates a builder based on this context. Newly created context will contain an independent value store but the values themselves will be the same.
   *
   * @return builder
   */
  public Builder enrich() {
    return Builder.of(this);
  }

  /**
   * Creates a new context with the provided value stored inside in addition to existing ones.
   *
   * @param value to be included in the context
   * @param <T>   value type
   * @return new immutable context
   */
  public <T> PageContext enrich(T value) {
    return Builder.of(this)
        .put((Class<T>) value.getClass(), value)
        .build();
  }

  /**
   * Creates a new context with the provided value stored inside in addition to existing ones.
   *
   * @param clazz key under which value will be registered
   * @param value to be included in the context
   * @param <T>   value type
   * @return new immutable context
   */
  public <T> PageContext enrich(Class<T> clazz, T value) {
    return Builder.of(this)
        .put(clazz, value)
        .build();
  }

  /**
   * Creates a new context with the provided values stored inside in addition to existing ones.
   *
   * @param value        to be included in the context
   * @param anotherValue to be included in the context
   * @param <T>          first value type
   * @param <T2>         second value type
   * @return new immutable context
   */
  public <T, T2> PageContext enrich(T value, T2 anotherValue) {
    return Builder.of(this)
        .put((Class<T>) value.getClass(), value)
        .put((Class<T2>) anotherValue.getClass(), anotherValue)
        .build();
  }

  /**
   * Creates a new context with the provided values stored inside in addition to existing ones.
   *
   * @param clazz        key under which the first value will be registered
   * @param value        to be included in the context
   * @param anotherClazz key under which the second value will be registered
   * @param anotherValue to be included in the context
   * @param <T>          first value type
   * @param <T2>         second value type
   * @return new immutable context
   */
  public <T, T2> PageContext enrich(Class<T> clazz, T value, Class<T2> anotherClazz, T2 anotherValue) {
    return Builder.of(this)
        .put(clazz, value)
        .put(anotherClazz, anotherValue)
        .build();
  }

  public static class Builder {

    private final Map<Class<?>, Object> context;

    public Builder(Map<Class<?>, Object> context) {
      this.context = context;
    }

    public static Builder instance() {
      return new Builder(new HashMap<>());
    }

    public static Builder of(PageContext context) {
      return new Builder(new HashMap<>(context.context));
    }

    public <T> Builder put(Class<T> clazz, T value) {
      context.put(clazz, value);
      return this;
    }

    public PageContext build() {
      return new PageContext(context);
    }

  }

}
