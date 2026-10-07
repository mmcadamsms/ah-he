package com.ahhe.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class WebApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebApiApplication.class, args);
  }
}

