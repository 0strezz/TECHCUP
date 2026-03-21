package edu.eci.dosw.tech_cup;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @BeforeAll
    static void setup() {
        // Esto carga las variables antes de que Spring intente conectar a la BD
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(entry -> 
            System.setProperty(entry.getKey(), entry.getValue())
        );
    }

    @Test
    void contextLoads() {
        // Este test ahora pasará porque ya conoce las credenciales
    }
}