package com.CrimsonBackendDatabase.crimsondb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CrimsondbApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrimsondbApplication.class, args);
	}

}
