package com.example.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.example")
@EnableJpaRepositories(basePackages = "com.example.common.repository")
@EntityScan(basePackages = "com.example.common.entity")
public class BatchApplication {
  public static void main(String[] args) {
    SpringApplication.run(BatchApplication.class, args);
  }

}
