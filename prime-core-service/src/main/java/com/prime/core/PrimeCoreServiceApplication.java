package com.prime.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@EnableAsync
@EnableCaching
@SpringBootApplication(exclude = {ThymeleafAutoConfiguration.class})
public class PrimeCoreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrimeCoreServiceApplication.class, args);
    }

}
