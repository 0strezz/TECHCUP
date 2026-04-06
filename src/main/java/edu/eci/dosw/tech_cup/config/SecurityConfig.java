package edu.eci.dosw.tech_cup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frame -> frame.deny())
                )
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Públicos
                        .requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/refresh").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/tournaments", "/api/tournaments/*").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // Cualquier usuario autenticado
                        .requestMatchers(HttpMethod.GET, "/api/auth/me").authenticated()

                        // Solo Administrador
                        .requestMatchers(HttpMethod.GET, "/api/users", "/api/users/*").hasRole("ADMINISTRATOR")
                        .requestMatchers(HttpMethod.PUT, "/api/users/*").hasRole("ADMINISTRATOR")
                        .requestMatchers(HttpMethod.PATCH, "/api/users/*/inactive").hasRole("ADMINISTRATOR")
                        .requestMatchers(HttpMethod.PATCH, "/api/users/*/role").hasRole("ADMINISTRATOR")

                        // Organizer o Administrator para torneos
                        .requestMatchers(HttpMethod.POST, "/api/tournaments").hasAnyRole("ORGANIZER", "ADMINISTRATOR")
                        .requestMatchers(HttpMethod.PUT, "/api/tournaments/*").hasAnyRole("ORGANIZER", "ADMINISTRATOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/tournaments/*").hasAnyRole("ORGANIZER", "ADMINISTRATOR")
                        .requestMatchers(HttpMethod.PATCH, "/api/tournaments/*/start").hasAnyRole("ORGANIZER", "ADMINISTRATOR")
                        .requestMatchers(HttpMethod.PATCH, "/api/tournaments/*/finish").hasAnyRole("ORGANIZER", "ADMINISTRATOR")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}