package edu.eci.dosw.tech_cup.controller;

import edu.eci.dosw.tech_cup.service.AuthService;
import edu.eci.dosw.tech_cup.dto.LoginRequestDto;
import edu.eci.dosw.tech_cup.dto.LoginResponseDto;
import edu.eci.dosw.tech_cup.dto.RefreshTokenRequestDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        LoginResponseDto response = authService.login(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/me")
    public ResponseEntity<LoginResponseDto> getMe(@RequestHeader("Authorization") String token) {
        LoginResponseDto response = authService.getMe(token);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(@RequestBody RefreshTokenRequestDto request) {
        LoginResponseDto response = authService.refreshToken(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
