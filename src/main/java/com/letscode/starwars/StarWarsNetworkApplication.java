package com.letscode.starwars;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Que a força esteja com você", version = "1.0", description = "Star Wars Network API"))
public class StarWarsNetworkApplication {
	public static void main(String[] args) {
		SpringApplication.run(StarWarsNetworkApplication.class, args);
	}
}
