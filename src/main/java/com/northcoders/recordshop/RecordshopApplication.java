package com.northcoders.recordshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RecordshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecordshopApplication.class, args);
	}

}
