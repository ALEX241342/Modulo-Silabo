package com.microservicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
@EnableAutoConfiguration(exclude = {
		EurekaClientAutoConfiguration.class,
		EurekaDiscoveryClientConfiguration.class,
		LoadBalancerAutoConfiguration.class
})
public class MicroservicioSilaboApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioSilaboApplication.class, args);
	}

}
