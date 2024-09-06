package com.where.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.FunctionalSpringApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class WhereApplication {

	public static void main(String[] args) {
		FunctionalSpringApplication.run(WhereApplication.class, args);
	}

}
