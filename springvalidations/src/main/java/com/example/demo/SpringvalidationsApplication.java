package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.validation.annotations.EnableDataResolver;

@SpringBootApplication
@EnableDataResolver
public class SpringvalidationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringvalidationsApplication.class, args);
	}
	
	/*@Bean
	public ValidatorFactoryBean validator() {
	   return new ValidatorFactoryBean();
	}*/

}
