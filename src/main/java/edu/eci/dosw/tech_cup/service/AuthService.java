package edu.eci.dosw.tech_cup.service;

import edu.eci.dosw.tech_cup.dto.LoginRequestDto;
import edu.eci.dosw.tech_cup.dto.LoginResponseDto;
import edu.eci.dosw.tech_cup.dto.RefreshTokenRequestDto;
import edu.eci.dosw.tech_cup.entity.auth.TokenEntity;
import edu.eci.dosw.tech_cup.entity.auth.TokenType;
import edu.eci.dosw.tech_cup.entity.user.UserEntity;
import edu.eci.dosw.tech_cup.repository.TokenRepository;
import edu.eci.dosw.tech_cup.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, TokenRepository tokenRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDto login(LoginRequestDto request) {
        log.info("Login attempt for email={}", request.getEmail());

        Optional<UserEntity> opt = userRepository.findByEmail(request.getEmail());
        if (opt.isEmpty()) {
            log.debug("Login failed. user not found for email={}", request.getEmail());
            return loginError("Invalid email or password");
        }

        UserEntity user = opt.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.debug("Login failed. wrong password for email={}", request.getEmail());
            return loginError("Invalid email or password");
        }

        if (!user.isActive()) {
            log.debug("Login failed. user is inactive email={}", request.getEmail());
            return loginError("User account is inactive");
        }

        revokeRefreshTokens(user.getId());

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveRefreshToken(user, refreshToken);

        log.info("Login successful for email={}", request.getEmail());
        return buildAuthResponse(user, accessToken, refreshToken, "Login successful");
    }

    public LoginResponseDto refreshToken(RefreshTokenRequestDto request) {
        String refreshToken = request.getRefreshToken();
        if (refreshToken == null || refreshToken.isBlank()) {
            return loginError("Refresh token is required");
        }

        Optional<TokenEntity> storedTokenOpt = tokenRepository.findByTokenAndRevokedFalseAndTokenType(refreshToken, TokenType.REFRESH);
        if (storedTokenOpt.isEmpty()) {
            return loginError("Invalid refresh token");
        }

        TokenEntity storedToken = storedTokenOpt.get();
        UserEntity user = storedToken.getUser();
        if (!user.isActive()) {
            return loginError("User account is inactive");
        }

        try {
            if (!jwtService.isRefreshTokenValid(refreshToken, user) || storedToken.getExpiresAt().isBefore(LocalDateTime.now())) {
                storedToken.setRevoked(true);
                tokenRepository.save(storedToken);
                return loginError("Refresh token has expired");
            }
        } catch (Exception e) {
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            return loginError("Invalid refresh token");
        }

        storedToken.setRevoked(true);
        tokenRepository.save(storedToken);

        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);
        saveRefreshToken(user, newRefreshToken);

        return buildAuthResponse(user, newAccessToken, newRefreshToken, "Token refreshed successfully");
    }

    public LoginResponseDto getMe(String tokenValue) {
        log.info("getMe requested. tokenPresent={}", tokenValue != null && !tokenValue.isEmpty());

        if (tokenValue == null || tokenValue.isEmpty()) {
            log.debug("Rejected getMe request due to missing token");
            return loginError("Invalid token");
        }

        String cleanToken = tokenValue.startsWith("Bearer ") ? tokenValue.substring(7) : tokenValue;
        String email;
        try {
            email = jwtService.extractSubject(cleanToken);
        } catch (Exception e) {
            return loginError("Invalid token");
        }

        Optional<UserEntity> opt = userRepository.findByEmail(email);
        if (opt.isEmpty()) {
            log.debug("Rejected getMe request. user not found for token subject");
            return loginError("Invalid token");
        }

        UserEntity user = opt.get();
        if (!user.isActive()) {
            return loginError("User account is inactive");
        }

        try {
            if (!jwtService.isAccessTokenValid(cleanToken, user)) {
                return loginError("Invalid token");
            }
        } catch (Exception e) {
            return loginError("Invalid token");
        }

        log.debug("getMe successful for userId={}", user.getId());
        return new LoginResponseDto(user.getId(), user.getName(), user.getEmail(), user.getRole(), "User details fetched successfully");
    }

    // --- Helper ---

    private LoginResponseDto loginError(String message) {
        return new LoginResponseDto(null, null, null, null, message);
    }

    private LoginResponseDto buildAuthResponse(UserEntity user, String accessToken, String refreshToken, String message) {
        return new LoginResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                accessToken,
                refreshToken,
                "Bearer",
                jwtService.getAccessTokenExpirationSeconds(),
                message
        );
    }

    private void saveRefreshToken(UserEntity user, String refreshToken) {
        TokenEntity token = new TokenEntity(
                refreshToken,
                LocalDateTime.now(),
                LocalDateTime.ofInstant(jwtService.extractExpirationInstant(refreshToken), ZoneOffset.UTC),
                TokenType.REFRESH,
                false,
                user
        );
        tokenRepository.save(token);
    }

    private void revokeRefreshTokens(java.util.UUID userId) {
        List<TokenEntity> activeTokens = tokenRepository.findByUserIdAndRevokedFalseAndTokenType(userId, TokenType.REFRESH);
        for (TokenEntity token : activeTokens) {
            token.setRevoked(true);
        }
        tokenRepository.saveAll(activeTokens);
    }
}
