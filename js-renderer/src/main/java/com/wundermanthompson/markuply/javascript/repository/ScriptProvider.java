package com.wundermanthompson.markuply.javascript.repository;

import reactor.core.publisher.Mono;

public interface ScriptProvider {

  Mono<String> getBundle();

}
