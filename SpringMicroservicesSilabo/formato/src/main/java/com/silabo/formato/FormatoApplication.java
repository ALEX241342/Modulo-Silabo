package com.silabo.formato;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@EnableJpaRepositories(basePackages = "com.silabo.repositorio")
@EntityScan(basePackages = "com.silabo.entidades")  // también ayuda a detectar tus entidades

@SpringBootApplication
public class FormatoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormatoApplication.class, args);
	}

}
