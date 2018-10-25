package com.dadazao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author John Li
 */
@EnableEurekaClient
@SpringBootApplication
public class BlogServiceLauncher {
    public static void main(String[] args) {
        SpringApplication.run(BlogServiceLauncher.class, args);
    }
}

