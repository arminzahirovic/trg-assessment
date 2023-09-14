package com.trgassessment.fleetsimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class FleetSimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FleetSimulatorApplication.class, args);
	}
}
