package edu.eci.dosw.tech_cup.service;

import edu.eci.dosw.tech_cup.dto.LoginRequestDto;
import edu.eci.dosw.tech_cup.dto.LoginResponseDto;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public LoginResponseDto login(LoginRequestDto request) {
        String dummyEmail = "dummy@example.com";
        String dummyPassword = "password123";

        // Valiating credentials
        if (request.getEmail().equals(dummyEmail) &&
            request.getPassword().equals(dummyPassword)) {
                return new LoginResponseDto(
                    UUID.randomUUID(),
                    "Dummy User",
                    dummyEmail,
                    "PLAYER",
                    "Login succesful"
                );
            }
        return new LoginResponseDto(
            null,
            null,
            null,
            null, 
            "Invalid email or password"
        );
    }

    public LoginResponseDto getMe(String token) {
        // In a real application, you would validate the token and fetch user details from the database
        if (token != null && !token.isEmpty()) {
            return new LoginResponseDto(
                UUID.randomUUID(),
                "Dummy User",
                "dummy@example.com",
                "PLAYER",
                "User details fetched successfully"
            );
        }
        return new LoginResponseDto(
            null,
            null,
            null,
            null,
            "Invalid token"
        );
    }

}
