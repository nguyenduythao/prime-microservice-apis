package com.prime.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication(proxyBeanMethods = false)
public class PrimeEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrimeEurekaServerApplication.class, args);
    }

}
