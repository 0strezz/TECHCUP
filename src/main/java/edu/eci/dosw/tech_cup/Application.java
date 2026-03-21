package edu.eci.dosw.tech_cup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
    // Especifica explícitamente que busque en el directorio raíz
    Dotenv dotenv = Dotenv.configure()
            .directory("./") 
            .ignoreIfMissing()
            .load();
    
    dotenv.entries().forEach(entry -> {
        System.setProperty(entry.getKey(), entry.getValue());
    });

    SpringApplication.run(Application.class, args);
	System.out.println("¡Aplicación iniciada con éxito!");
	}
}
