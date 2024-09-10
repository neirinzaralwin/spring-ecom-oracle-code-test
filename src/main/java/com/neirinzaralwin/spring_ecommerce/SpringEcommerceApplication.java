package com.neirinzaralwin.spring_ecommerce;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring ecommerce oracle project",
				version = "1.0.0",
				description = "API for E-commerce using spring boot framework.",
				termsOfService = "neirinzaralwin",
				contact = @Contact(
						name = "neirinzaralwin",
						email = "zaralwinneirin@gmail.com"
				),
				license = @License(
						name = "license",
						url = "MIT"
				)
		)

)
public class SpringEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringEcommerceApplication.class, args);
	}

}
