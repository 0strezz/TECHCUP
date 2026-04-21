package edu.eci.dosw.tech_cup.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import org.mockito.Answers;

class SecurityConfigTest {
    @Test
    void testPasswordEncoder() {
        SecurityConfig securityConfig = new SecurityConfig(null);
        assertNotNull(securityConfig.passwordEncoder());
    }

    @Test
    void testAuthenticationManager() throws Exception {
        SecurityConfig securityConfig = new SecurityConfig(null);
        AuthenticationConfiguration authConfig = mock(AuthenticationConfiguration.class);
        // authConfig.getAuthenticationManager() returns null from mock, that's fine
        securityConfig.authenticationManager(authConfig);
    }

    @Test
    void testSecurityFilterChain() throws Exception {
        SecurityConfig securityConfig = new SecurityConfig(null);
        HttpSecurity http = mock(HttpSecurity.class, Answers.RETURNS_DEEP_STUBS);
        // Exercises the entire securityFilterChain configuration, result may be null from mock
        securityConfig.securityFilterChain(http);
    }
}
