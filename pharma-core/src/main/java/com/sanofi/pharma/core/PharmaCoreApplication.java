package com.sanofi.pharma.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@ComponentScan(basePackages = "com.sanofi.pharma")
@EnableRetry
public class PharmaCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(PharmaCoreApplication.class, args);
    }

}
