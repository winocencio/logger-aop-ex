package com.winocencio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class LoggerExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoggerExampleApplication.class, args);
    }
}