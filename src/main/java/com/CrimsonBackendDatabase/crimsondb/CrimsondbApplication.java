package com.CrimsonBackendDatabase.crimsondb;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
public class CrimsondbApplication {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		SpringApplication.run(CrimsondbApplication.class, args);
	}

}
