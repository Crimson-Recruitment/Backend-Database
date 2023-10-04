package com.CrimsonBackendDatabase.crimsondb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableAsync
public class CrimsondbApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrimsondbApplication.class, args);
	}

}
