package io.wttech.markuply.examples.openlibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OpenBookApplication {

  public static void main(String[] args) {
    System.setProperty("reactor.netty.http.server.accessLogEnabled", "true");
    SpringApplication.run(OpenBookApplication.class, args);
  }

}
