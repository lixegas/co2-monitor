package com.lixegas.co2_monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Co2MonitorApplication {
	private static final Logger logger = LoggerFactory.getLogger(Co2MonitorApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(Co2MonitorApplication.class, args);
		logger.info("Application is running...");
	}
}
