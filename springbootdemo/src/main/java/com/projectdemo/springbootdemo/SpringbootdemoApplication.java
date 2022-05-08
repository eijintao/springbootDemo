package com.projectdemo.springbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
// @EnableConfigurationProperties()
public class SpringbootdemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringbootdemoApplication.class, args);
    }

}
