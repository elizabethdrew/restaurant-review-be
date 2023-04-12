package dev.drew.restaurantreview;

import dev.drew.restaurantreview.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class BasicDiningApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicDiningApplication.class, args);
	}

}