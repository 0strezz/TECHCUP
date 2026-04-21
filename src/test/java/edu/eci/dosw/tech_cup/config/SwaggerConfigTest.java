package edu.eci.dosw.tech_cup.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SwaggerConfigTest {
    @Test
    void testOpenAPIBean() {
        SwaggerConfig config = new SwaggerConfig();
        assertNotNull(config.customOpenAPI());
    }
}
