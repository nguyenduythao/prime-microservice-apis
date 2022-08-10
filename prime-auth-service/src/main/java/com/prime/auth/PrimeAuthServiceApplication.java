package com.prime.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableEurekaClient
@EnableDiscoveryClient
@EnableAsync
@EnableCaching
@SpringBootApplication
public class PrimeAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrimeAuthServiceApplication.class, args);
    }

}
