package main.java.edu.eci.dosw.tech_cup.service;

import java.util.UUID;

import main.java.edu.eci.dosw.tech_cup.dto.CreateUserRequestDto;
import main.java.edu.eci.dosw.tech_cup.dto.UpdateUserRequestDto;

public class UserService {
    String dummyName = "Dummy User";
    String dummyEmail = "dummyuser@example.com";
    
    public UserResponseDto createUser(CreateUserRequestDto request) {

        return new UserResponseDto (
            UUID.randomUUID(),
            request.getName(),
            request.getEmail(),
            "PLAYER",
            "User created succesfully"
        );
    }

    public UpdateUserDto updateUser (UpdateUserRequestDto request) {
        if (request.getName().equals(dummyName) && request.getEmail().equals(dummyEmail)) {
            
        }
        
}
