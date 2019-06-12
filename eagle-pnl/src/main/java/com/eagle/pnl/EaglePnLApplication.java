package com.eagle.pnl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EaglePnLApplication {

	public static void main(String[] args) {
		SpringApplication.run(EaglePnLApplication.class, args);

	}

}
