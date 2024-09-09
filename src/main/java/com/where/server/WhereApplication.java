package com.where.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.FunctionalSpringApplication;

@SpringBootApplication
public class WhereApplication {

	public static void main(String[] args) {
		FunctionalSpringApplication.run(WhereApplication.class, args);
	}

}
