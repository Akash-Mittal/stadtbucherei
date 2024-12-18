package com.stadtbucheri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DataApp {

	private static final Logger LOG = LoggerFactory.getLogger(DataApp.class);

	public static void main(String[] args) {
		LOG.info("Welcome to Bibliothem AM frankfurt Hauptwache");
		ConfigurableApplicationContext run = SpringApplication.run(DataApp.class, args);
		run.registerShutdownHook();
	}

}
