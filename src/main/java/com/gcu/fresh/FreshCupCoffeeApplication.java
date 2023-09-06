package com.gcu.fresh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.gcu")
public class FreshCupCoffeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreshCupCoffeeApplication.class, args);
	}

}
