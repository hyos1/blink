package com.example.blink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class BlinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlinkApplication.class, args);
	}
}