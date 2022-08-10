package com.prime.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@SpringBootApplication
public class PrimeUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrimeUserServiceApplication.class, args);
    }

}
