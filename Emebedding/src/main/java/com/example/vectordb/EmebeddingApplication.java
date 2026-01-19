package com.example.vectordb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.vectordb")
public class EmebeddingApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmebeddingApplication.class, args);
	}

}
