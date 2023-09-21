package dev.drew.restaurantreview;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class BasicDiningApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicDiningApplication.class, args);
	}

}