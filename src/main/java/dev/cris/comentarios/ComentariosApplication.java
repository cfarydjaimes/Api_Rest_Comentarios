package dev.cris.comentarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ComentariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComentariosApplication.class, args);
	}

}
