package com.wundermanthompson.markuply.engine;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MarkuplyAutoConfiguration.class})
public class BaseConfiguration {

}
