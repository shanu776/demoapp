package com.example.bootproject;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class BootProjectApplication {

	public static void main(String[] args) {
		//SpringApplication.run(BootProjectApplication.class, args);
		SpringApplicationBuilder builder = new SpringApplicationBuilder(BootProjectApplication.class);
		builder.headless(false);
		ConfigurableApplicationContext context = builder.run(args);
	}
}