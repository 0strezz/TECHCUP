package edu.eci.dosw.tech_cup.service;

import org.slf4j.*;

import edu.eci.dosw.tech_cup.dto.LoginRequestDto;
import edu.eci.dosw.tech_cup.dto.LoginResponseDto;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public LoginResponseDto login(LoginRequestDto request) {
        log.info("Login attempt for email={}", request.getEmail());
        String dummyEmail = "dummy@example.com";
        String dummyPassword = "password123";

        // Valiating credentials
        if (request.getEmail().equals(dummyEmail) &&
            request.getPassword().equals(dummyPassword)) {
                log.info("Login successful for email={}", request.getEmail());
                return new LoginResponseDto(
                    UUID.randomUUID(),
                    "Dummy User",
                    dummyEmail,
                    "PLAYER",
                    "Login succesful"
                );
            }
        log.debug("Login failed for email={}", request.getEmail());
        return new LoginResponseDto(
            null,
            null,
            null,
            null, 
            "Invalid email or password"
        );
    }

    public LoginResponseDto getMe(String token) {
        log.info("getMe requested. tokenPresent={}", token != null && !token.isEmpty());
        // In a real application, you would validate the token and fetch user details from the database
        if (token != null && !token.isEmpty()) {
            log.debug("Token accepted for getMe request");
            return new LoginResponseDto(
                UUID.randomUUID(),
                "Dummy User",
                "dummy@example.com",
                "PLAYER",
                "User details fetched successfully"
            );
        }
        log.debug("Rejected getMe request due to invalid token");
        return new LoginResponseDto(
            null,
            null,
            null,
            null,
            "Invalid token"
        );
    }

}
