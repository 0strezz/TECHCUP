package main.java.edu.eci.dosw.tech_cup.service;

import edu.eci.dosw.tech_cup.dto.LoginRequestDto;
import edu.eci.dosw.tech_cup.dto.LoginResponseDto;
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
}
