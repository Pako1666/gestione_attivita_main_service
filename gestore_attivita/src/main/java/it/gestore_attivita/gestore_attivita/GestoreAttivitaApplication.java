package it.gestore_attivita.gestore_attivita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.test.context.ActiveProfiles;

@EnableKafka
@SpringBootApplication
public class GestoreAttivitaApplication {


	public static void main(String[] args) {
		SpringApplication.run(GestoreAttivitaApplication.class, args);
	}

}
