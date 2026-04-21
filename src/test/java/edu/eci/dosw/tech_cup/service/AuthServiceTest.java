package edu.eci.dosw.tech_cup.service;

import edu.eci.dosw.tech_cup.dto.LoginRequestDto;
import edu.eci.dosw.tech_cup.dto.LoginResponseDto;
import edu.eci.dosw.tech_cup.dto.RefreshTokenRequestDto;
import edu.eci.dosw.tech_cup.entity.auth.TokenEntity;
import edu.eci.dosw.tech_cup.entity.auth.TokenType;
import edu.eci.dosw.tech_cup.entity.user.UserEntity;
import edu.eci.dosw.tech_cup.repository.TokenRepository;
import edu.eci.dosw.tech_cup.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService tests")
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private UserEntity activeUser;
    private UserEntity inactiveUser;
    private TokenEntity validToken;

    @BeforeEach
    void setUp() {
        activeUser = new UserEntity("Active", "a@a.com", "pass", true, LocalDateTime.now());
        activeUser.setId(UUID.randomUUID());

        inactiveUser = new UserEntity("Inactive", "i@i.com", "pass", false, LocalDateTime.now());
        inactiveUser.setId(UUID.randomUUID());

        validToken = new TokenEntity("refresh_token", LocalDateTime.now(), LocalDateTime.now().plusDays(1), TokenType.REFRESH, false, activeUser);
    }

    @Test
    void login_UserNotFound() {
        when(userRepository.findByEmail("u@u.com")).thenReturn(Optional.empty());
        LoginResponseDto res = authService.login(new LoginRequestDto("u@u.com", "pass"));
        assertEquals("Invalid email or password", res.getMessage());
    }

    @Test
    void login_WrongPassword() {
        when(userRepository.findByEmail("a@a.com")).thenReturn(Optional.of(activeUser));
        when(passwordEncoder.matches("wrong", "pass")).thenReturn(false);
        LoginResponseDto res = authService.login(new LoginRequestDto("a@a.com", "wrong"));
        assertEquals("Invalid email or password", res.getMessage());
    }

    @Test
    void login_InactiveUser() {
        when(userRepository.findByEmail("i@i.com")).thenReturn(Optional.of(inactiveUser));
        when(passwordEncoder.matches("pass", "pass")).thenReturn(true);
        LoginResponseDto res = authService.login(new LoginRequestDto("i@i.com", "pass"));
        assertEquals("User account is inactive", res.getMessage());
    }

    @Test
    void login_Success() {
        when(userRepository.findByEmail("a@a.com")).thenReturn(Optional.of(activeUser));
        when(passwordEncoder.matches("pass", "pass")).thenReturn(true);
        when(tokenRepository.findByUserIdAndRevokedFalseAndTokenType(activeUser.getId(), TokenType.REFRESH)).thenReturn(List.of(validToken));
        when(jwtService.generateAccessToken(activeUser)).thenReturn("access");
        when(jwtService.generateRefreshToken(activeUser)).thenReturn("refresh");
        when(jwtService.extractExpirationInstant("refresh")).thenReturn(Instant.now().plusSeconds(100));

        LoginResponseDto res = authService.login(new LoginRequestDto("a@a.com", "pass"));

        assertEquals("Login successful", res.getMessage());
        assertEquals("access", res.getAccessToken());
        assertEquals("refresh", res.getRefreshToken());
        assertTrue(validToken.isRevoked()); // check revoke happened
        verify(tokenRepository).save(any(TokenEntity.class)); // check new token saved
    }

    @Test
    void refreshToken_EmptyToken() {
        LoginResponseDto res = authService.refreshToken(new RefreshTokenRequestDto(""));
        assertEquals("Refresh token is required", res.getMessage());
    }

    @Test
    void refreshToken_NotFound() {
        when(tokenRepository.findByTokenAndRevokedFalseAndTokenType("notfound", TokenType.REFRESH)).thenReturn(Optional.empty());
        LoginResponseDto res = authService.refreshToken(new RefreshTokenRequestDto("notfound"));
        assertEquals("Invalid refresh token", res.getMessage());
    }

    @Test
    void refreshToken_UserInactive() {
        validToken.setUser(inactiveUser);
        when(tokenRepository.findByTokenAndRevokedFalseAndTokenType("rt", TokenType.REFRESH)).thenReturn(Optional.of(validToken));
        LoginResponseDto res = authService.refreshToken(new RefreshTokenRequestDto("rt"));
        assertEquals("User account is inactive", res.getMessage());
    }

    @Test
    void refreshToken_ExpiredOrInvalid() {
        validToken.setExpiresAt(LocalDateTime.now().minusDays(1)); // Expired
        when(tokenRepository.findByTokenAndRevokedFalseAndTokenType("rt", TokenType.REFRESH)).thenReturn(Optional.of(validToken));
        when(jwtService.isRefreshTokenValid("rt", activeUser)).thenReturn(true); // JWT valid but DB expired
        
        LoginResponseDto res = authService.refreshToken(new RefreshTokenRequestDto("rt"));
        assertEquals("Refresh token has expired", res.getMessage());
        assertTrue(validToken.isRevoked());
    }

    @Test
    void refreshToken_JwtException() {
        when(tokenRepository.findByTokenAndRevokedFalseAndTokenType("rt", TokenType.REFRESH)).thenReturn(Optional.of(validToken));
        when(jwtService.isRefreshTokenValid("rt", activeUser)).thenThrow(new RuntimeException("invalid"));
        
        LoginResponseDto res = authService.refreshToken(new RefreshTokenRequestDto("rt"));
        assertEquals("Invalid refresh token", res.getMessage());
        assertTrue(validToken.isRevoked());
    }

    @Test
    void refreshToken_Success() {
        when(tokenRepository.findByTokenAndRevokedFalseAndTokenType("rt", TokenType.REFRESH)).thenReturn(Optional.of(validToken));
        when(jwtService.isRefreshTokenValid("rt", activeUser)).thenReturn(true);
        when(jwtService.generateAccessToken(activeUser)).thenReturn("new_access");
        when(jwtService.generateRefreshToken(activeUser)).thenReturn("new_refresh");
        when(jwtService.extractExpirationInstant("new_refresh")).thenReturn(Instant.now().plusSeconds(100));

        LoginResponseDto res = authService.refreshToken(new RefreshTokenRequestDto("rt"));
        assertEquals("Token refreshed successfully", res.getMessage());
        assertEquals("new_access", res.getAccessToken());
        assertTrue(validToken.isRevoked()); // old token revoked
    }

    @Test
    void getMe_NullToken() {
        LoginResponseDto res = authService.getMe(null);
        assertEquals("Invalid token", res.getMessage());
    }

    @Test
    void getMe_JwtException() {
        when(jwtService.extractSubject("badtoken")).thenThrow(new RuntimeException("bad"));
        LoginResponseDto res = authService.getMe("Bearer badtoken");
        assertEquals("Invalid token", res.getMessage());
    }

    @Test
    void getMe_UserNotFound() {
        when(jwtService.extractSubject("token")).thenReturn("u@u.com");
        when(userRepository.findByEmail("u@u.com")).thenReturn(Optional.empty());
        LoginResponseDto res = authService.getMe("token");
        assertEquals("Invalid token", res.getMessage());
    }

    @Test
    void getMe_UserInactive() {
        when(jwtService.extractSubject("token")).thenReturn("i@i.com");
        when(userRepository.findByEmail("i@i.com")).thenReturn(Optional.of(inactiveUser));
        LoginResponseDto res = authService.getMe("token");
        assertEquals("User account is inactive", res.getMessage());
    }

    @Test
    void getMe_InvalidJwtForUser() {
        when(jwtService.extractSubject("token")).thenReturn("a@a.com");
        when(userRepository.findByEmail("a@a.com")).thenReturn(Optional.of(activeUser));
        when(jwtService.isAccessTokenValid("token", activeUser)).thenReturn(false);
        LoginResponseDto res = authService.getMe("token");
        assertEquals("Invalid token", res.getMessage());
    }

    @Test
    void getMe_JwtValidException() {
        when(jwtService.extractSubject("token")).thenReturn("a@a.com");
        when(userRepository.findByEmail("a@a.com")).thenReturn(Optional.of(activeUser));
        when(jwtService.isAccessTokenValid("token", activeUser)).thenThrow(new RuntimeException("err"));
        LoginResponseDto res = authService.getMe("token");
        assertEquals("Invalid token", res.getMessage());
    }

    @Test
    void getMe_Success() {
        when(jwtService.extractSubject("token")).thenReturn("a@a.com");
        when(userRepository.findByEmail("a@a.com")).thenReturn(Optional.of(activeUser));
        when(jwtService.isAccessTokenValid("token", activeUser)).thenReturn(true);
        LoginResponseDto res = authService.getMe("token");
        assertEquals("User details fetched successfully", res.getMessage());
        assertEquals("a@a.com", res.getEmail());
    }
}
