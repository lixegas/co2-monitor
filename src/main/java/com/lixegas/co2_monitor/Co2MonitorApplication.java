package com.lixegas.co2_monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Co2MonitorApplication {
	public static void main(String[] args) {
		SpringApplication.run(Co2MonitorApplication.class, args);
	}

}
