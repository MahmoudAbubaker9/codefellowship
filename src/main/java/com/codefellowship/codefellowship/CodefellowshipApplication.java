package com.codefellowship.codefellowship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CodefellowshipApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CodefellowshipApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(CodefellowshipApplication.class, args);
	}

}
