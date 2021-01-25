package com.wundermanthompson.markuply.engine.request.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Methods marked with this annotation will be cached within the context of a single request.
 * </p>
 * <p>
 * Annotated types will behave as if all their methods were annotated.
 * </p>
 * <p>
 * For caching to work properly make sure all parameters implement {@link Object#hashCode()} and
 * {@link Object#equals(Object)}.
 * </p>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestCache {

}
