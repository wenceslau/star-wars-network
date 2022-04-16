package com.letscode.starwars;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Que a força esteja com você", version = "1.0", description = "Star Wars Network API"))
public class StarWarsNetworkApplication {
	public static void main(String[] args) throws Exception {

		double d = 1243123.1;
		DecimalFormat df = new DecimalFormat("###,###.00");
		System.out.println(df.format(d));

		Locale meuLocal = new Locale( "pt", "BR" );
		NumberFormat nfVal = NumberFormat.getCurrencyInstance( meuLocal );
		System.out.println(nfVal.format(d));


		System.out.println(ZoneId.getAvailableZoneIds());

		String time = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).format(DateTimeFormatter.ofPattern("HH:mm"));
		System.out.println(time);

		//SpringApplication.run(StarWarsNetworkApplication.class, args);
	}
}
