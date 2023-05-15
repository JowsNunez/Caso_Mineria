package com.principal.api_congestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@Configuration
@AutoConfiguration
@EntityScan(basePackages = { "com.entities" })
@EnableJpaRepositories("com.repositories")
@ComponentScan(basePackages = { "com.entities", "com.repositories", "com.services", "com.controladores",
		"com.mensajes" })
public class ApiCongestionApplication {


	public static void main(String[] args) {
		SpringApplication.run(ApiCongestionApplication.class, args);
	}

}
