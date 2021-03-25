package io.wttech.markuply.engine.request.cache;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Aspect
@Slf4j
public class RequestCacheAspect {

  @Pointcut("@within(io.wttech.markuply.engine.request.cache.RequestCache)")
  public void withinAnnotatedClass() {
  }

  @Pointcut("@annotation(io.wttech.markuply.engine.request.cache.RequestCache)")
  public void annotatedMethod() {
  }

  @Pointcut("execution(reactor.core.publisher.Mono+ *(..))")
  public void monoReturnType() {
  }

  @Pointcut("execution(reactor.core.publisher.Flux+ *(..))")
  public void fluxReturnType() {
  }

  @Around("(withinAnnotatedClass() || annotatedMethod()) && monoReturnType()")
  public Mono<?> cacheMono(ProceedingJoinPoint pjp) {
    return Mono.deferContextual(Mono::just).flatMap(context -> {
      RequestScopedCache requestScopedCache = context.get(RequestScopedCache.class);
      CacheKey cacheKey = calculateKey(pjp);
      Object result = requestScopedCache.findCachedResult(cacheKey);
      if (result == RequestScopedCache.NONE) {
        log.debug("Cache miss for method {}.{}()", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());
        try {
          Mono<?> cachedMethodResult = (Mono<?>) pjp.proceed();
          result = requestScopedCache.save(cacheKey, cachedMethodResult.cache());
        } catch (Throwable throwable) {
          return Mono.error(throwable);
        }
      } else {
        log.debug("Cache hit for method {}.{}()", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());
      }
      return result == RequestScopedCache.NULL ? Mono.empty() : (Mono<?>) result;
    });

  }

  @Around("(withinAnnotatedClass() || annotatedMethod()) && fluxReturnType()")
  public Flux<?> cacheFlux(ProceedingJoinPoint pjp) {
    return Mono.deferContextual(Mono::just).flatMapMany(context -> {
      RequestScopedCache requestScopedCache = context.get(RequestScopedCache.class);
      CacheKey cacheKey = calculateKey(pjp);
      Object result = requestScopedCache.findCachedResult(cacheKey);
      if (result == RequestScopedCache.NONE) {
        log.debug("Cache miss for method {}.{}()", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());
        try {
          Flux<?> cachedMethodResult = (Flux<?>) pjp.proceed();
          result = requestScopedCache.save(cacheKey, cachedMethodResult.cache());
        } catch (Throwable throwable) {
          return Flux.error(throwable);
        }
      } else {
        log.debug("Cache hit for method {}.{}()", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());
      }
      return result == RequestScopedCache.NULL ? Flux.empty() : (Flux<?>) result;
    });

  }

  private CacheKey calculateKey(ProceedingJoinPoint pjp) {
    return CacheKey.of(
        pjp.getSignature().getDeclaringType(),
        pjp.getSignature().getName(),
        pjp.getArgs()
    );
  }

}

