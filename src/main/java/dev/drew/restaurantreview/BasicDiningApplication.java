package dev.drew.restaurantreview;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
			title = "Restaurant Review REST API",
			description = "Spring Boot Restaurant Review REST APIs Documentation",
			version = "v1.0",
			contact = @Contact(
				name = "Elizabeth Drew",
				email = "hello@elizabethdrew.co"
			),
			license = @License(
				name = "License Name",
				url = "https://www.test.com/license"
			)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Restaurant Review Documentation",
				url = "https://github.com"
		)
)
public class BasicDiningApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicDiningApplication.class, args);
	}

}