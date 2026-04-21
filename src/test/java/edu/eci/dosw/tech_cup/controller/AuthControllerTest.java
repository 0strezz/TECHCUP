package edu.eci.dosw.tech_cup.controller;

import edu.eci.dosw.tech_cup.dto.LoginRequestDto;
import edu.eci.dosw.tech_cup.dto.LoginResponseDto;
import edu.eci.dosw.tech_cup.dto.RefreshTokenRequestDto;
import edu.eci.dosw.tech_cup.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthController tests")
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void login_Success() {
        LoginRequestDto req = new LoginRequestDto("test@test.com", "pass");
        LoginResponseDto res = new LoginResponseDto();
        res.setMessage("Login successful");

        when(authService.login(req)).thenReturn(res);

        ResponseEntity<LoginResponseDto> response = authController.login(req);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(res, response.getBody());
    }

    @Test
    void login_Failure() {
        LoginRequestDto req = new LoginRequestDto("test@test.com", "pass");
        LoginResponseDto res = new LoginResponseDto();
        res.setMessage("Invalid email or password");

        when(authService.login(req)).thenReturn(res);

        ResponseEntity<LoginResponseDto> response = authController.login(req);
        assertEquals(200, response.getStatusCode().value());
    }
    
    @Test
    void refreshToken_Success() {
        RefreshTokenRequestDto req = new RefreshTokenRequestDto("token");
        LoginResponseDto res = new LoginResponseDto();
        res.setMessage("Token refreshed successfully");

        when(authService.refreshToken(req)).thenReturn(res);

        ResponseEntity<LoginResponseDto> response = authController.refresh(req);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void refreshToken_Failure() {
        RefreshTokenRequestDto req = new RefreshTokenRequestDto("token");
        LoginResponseDto res = new LoginResponseDto();
        res.setMessage("Invalid refresh token");

        when(authService.refreshToken(req)).thenReturn(res);

        ResponseEntity<LoginResponseDto> response = authController.refresh(req);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void getMe_Success() {
        LoginResponseDto res = new LoginResponseDto();
        res.setMessage("User details fetched successfully");

        when(authService.getMe("Bearer token")).thenReturn(res);

        ResponseEntity<LoginResponseDto> response = authController.getMe("Bearer token");
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void getMe_Failure() {
        LoginResponseDto res = new LoginResponseDto();
        res.setMessage("Invalid token");

        when(authService.getMe("Bearer token")).thenReturn(res);

        ResponseEntity<LoginResponseDto> response = authController.getMe("Bearer token");
        assertEquals(200, response.getStatusCode().value());
    }
}
