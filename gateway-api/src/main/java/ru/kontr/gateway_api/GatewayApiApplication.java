package ru.kontr.gateway_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class GatewayApiApplication {

	static void main(String[] args) {
		SpringApplication.run(GatewayApiApplication.class, args);
	}

}
