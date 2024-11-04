package com.devs.dll.sm_eureka_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SmEurekaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmEurekaServiceApplication.class, args);
	}

}
