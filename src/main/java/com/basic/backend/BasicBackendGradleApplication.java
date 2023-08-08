package com.basic.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class BasicBackendGradleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicBackendGradleApplication.class, args);
    }

}
