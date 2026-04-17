package edu.eci.dosw.tech_cup.controller;

import edu.eci.dosw.tech_cup.service.AuthService;
import org.springframework.security.core.Authentication;
import edu.eci.dosw.tech_cup.dto.LoginRequestDto;
import edu.eci.dosw.tech_cup.dto.LoginResponseDto;
import edu.eci.dosw.tech_cup.dto.RefreshTokenRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Operations related to authentication and JWT management")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticates a user and returns access and refresh tokens.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        LoginResponseDto response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/me")
    @Operation(
            summary = "Get authenticated user information",
            description = "Returns the information of the currently authenticated user.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authenticated user retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<LoginResponseDto> getMe(Authentication authentication) {
        LoginResponseDto response = authService.getMe(authentication.getName());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token", description = "Generates a new access token using a valid refresh token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid refresh token")
    })
    public ResponseEntity<LoginResponseDto> refresh(@RequestBody RefreshTokenRequestDto request) {
        LoginResponseDto response = authService.refreshToken(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}