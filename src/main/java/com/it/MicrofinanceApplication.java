package com.it;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MicrofinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrofinanceApplication.class, args);
	}

}
